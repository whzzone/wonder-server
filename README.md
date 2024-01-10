### 简介
一个灵活的数据权限管理系统

优点：传统的5种权限：全部数据权限、本部门数据权限、本部门及下属部门数据、仅本人数据、自定义数据权限，其实再怎么自定义也是针对人和部门的纬度来说的，如果需求是：角色A只能订单表的销售数量>=100的订单，角色B只能订单表的销售数量<100的订单，恐怕实现不了。本项目针对这种需求就设计得更灵活了一点，仅需要针对不同的接口可视化添加一些配置即可以针对数据库表不同字段来控制数据权限。

缺点：如果是控制比较复杂的查询，需要在项目代码中预先写好相关的方法来提供使用。执行时间慢那么一点，但也只是对有限制的接口来说，还是可以接受的。

#### 启动报错及解决办法

> 启动报错：java: 服务配置文件不正确, 或构造处理程序对象javax.annotation.processing.Processor: Provider com.gitee.whzzone.processor.QueryProcessor not found时抛出异常错误
>
> 解决办法：在wonder-server模块依次执行mvn clean、mvn install即可
>
> 对wonder-processor代码修改后都要执行以上方法

#### 前后端地址
账号 admin/123456

前端 https://gitee.com/whzzone/wonder-web 仅仅用来可视化测试

后端 https://gitee.com/whzzone/wonder-server

#### 当前数据权限思路
使用MyBatis-Plus提供的`DataPermissionHandler`数据权限插件，使用自定义注解`@DataScope`在方法上，通过切面查询该账号拥有的角色List，是否关联有的数据规则，执行SQL前进行拦截，解析拼接SQL。

如果想限制同一张表的多个字段怎么办？比如某条规则限制`order`表查询付款金额大于2000元 && 已经完成的订单时怎么办？
- 方案一：优先考虑提供类型为`值`的情况是否满足当前需求。~~如需要限制两个及以上字段时，使用提供类型为`方法`来处理，此时配置记录中字段`column_name`为`id`，配置对应的无参或有参方法，返回`idList`去`IN`，就能满足。例如问题中可一执行一个方法返回 付款金额大于100元 && 已经完成的订单 的`idList`去`order`表拼接`id IN( idList )` 就能满足~~
- 方案二：已实现多规则拼接，支持`OR`、`AND`

测试账号 user01/123456

例子 我们想在订单不分页接口限制某个角色只能查看`收获地址中存在钦北区`或者`订单金额小于等于100`的订单。

那么我们可以添加一个`mark`（我这里暂时把他叫做标记），标记这个接口为`order-list`。![image-20231205112116030](https://cdn.weihuazhou.top/blog/2023/12/image-20231205112116030.png)

在这个标记下添加我们需要的两条规则![image-20231205112231485](https://cdn.weihuazhou.top/blog/2023/12/image-20231205112231485.png)

![image-20231205112244807](https://cdn.weihuazhou.top/blog/2023/12/image-20231205112244807.png)

![image-20231205112253529](https://cdn.weihuazhou.top/blog/2023/12/image-20231205112253529.png)

再给`普通角色`配置一下他在这个接口适用的规则，就是刚刚添加的规则

![image-20231205112841923](https://cdn.weihuazhou.top/blog/2023/12/image-20231205112841923.png)

然后使用注解`@DataScope("order-list")`在mapper的接口上，这里我们用重写MyBatis的list()接口来测试，如下：

```java
// 建议使用在mapper方法接口上
@Mapper 
public interface OrderMapper extends BaseMapper<Order> {
    
    @DataScope("order-list")
    @Override
    List<Order> selectList(@Param(Constants.WRAPPER) Wrapper<Order> queryWrapper);
    
}


// 用在service层方法上时，要注意调用的方法是否是本类的方法
// 如果是，会导致代理失败，数据权限失效
@Service
public class OrderServiceImpl extends EntityServiceImpl<OrderMapper, Order, OrderDto, OrderQuery> implements OrderService {
    
    @Override
    public List<OrderDto> list(OrderQuery query) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(query.getReceiverName()), Order::getReceiverName, query.getReceiverName());
        // ...其他条件
        return afterQueryHandler(list(queryWrapper), new BOrderQueryHandler());
    }
    
}
```

在无其他查询条件调用`OrderServiceImpl#list(OrderQuery query)`方法时，原本的sql为` SELECT * FROM ex_order WHERE  deleted=0`

经过数据权限处理后则为` SELECT * FROM ex_order WHERE deleted = 0 AND (order_amount <= 100 OR receiver_address LIKE '%钦北区%')`

从而实现了数据权限的控制。

当然这只是简单需求，复杂的需求也提供了解决办法：规则处可以配置全限定类名加方法，可以带参，解析规则时会反射执行该方法得到返回值，该功能可以实现复杂的数据权限需求，就看你怎么搭配。

### 不想写crud？提供增删改查接口

因为我懒，不想写增删改查接口，所以写了一套基础的增删改查，主要是由`EntityController`、`EntityService`、`EntityServiceImpl`完成。只要按规范继承了相关类，即可拥有增删改查功能。

maven引入
```java
<dependency>
    <groupId>com.gitee.whzzone</groupId>
    <artifactId>wonder-web</artifactId>
    <version>1.2</version>
</dependency>
```

#### 核心

- `EntityController` 提供6个基础的增删改查接口
- `EntityService` 定义基础的接口
- `EntityServiceImpl` 提供默认实现

就拿图书表简单的举个例子

1. 建立一张数据库表

    ```sql
    CREATE TABLE `ex_book` (
      `id` bigint NOT NULL,
      `name` varchar(20) DEFAULT NULL COMMENT '名称',
      `number` int DEFAULT NULL COMMENT '数量',
      `status` int DEFAULT NULL COMMENT '状态 0-维护中，1-正常，2-借出',
      `price` decimal(8,2) DEFAULT NULL COMMENT '单价',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `create_by` bigint DEFAULT NULL COMMENT '创建人',
      `update_time` datetime DEFAULT NULL COMMENT '更新时间',
      `update_by` bigint DEFAULT NULL COMMENT '更新人',
      `deleted` bit(1) DEFAULT NULL COMMENT '已删除',
      PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='图书管理';
    ```

2. 生成代码，来到`wonder-generator`模块`com.gitee.whzzone.generator.Generator`，填写好数据库、表、代码生成位置

   ```java
   // 数据库连接配置
   private static final String JDBC_URL = "jdbc:mysql://ip:3306/wonder?useUnicode=true&useSSL=false&characterEncoding=utf8";
   private static final String JDBC_USER_NAME = "root";
   private static final String JDBC_PASSWORD = "123456";
   
   // 包名和模块名
   private static final String PACKAGE_NAME = "com.gitee.whzzone.admin.business";
   private static final String MODULE_NAME = "";
   
   // 表名，多个表使用英文逗号分割
   private static final String[] TBL_NAMES = {"ex_book"};
   
   // 表名的前缀，从表生成代码时会去掉前缀
   private static final String TABLE_PREFIX = "ex_";
   ```

3. 启动`main`方法即可，会生成如下8个文件，然后启动项目进入项目swagger文档，即可拥有增删改查接口
   ![image-20231205145954783](https://cdn.weihuazhou.top/blog/2023/12/image-20231205145954783.png)![image-20231205150228822](https://cdn.weihuazhou.top/blog/2023/12/image-20231205150228822.png)


#### 生成的代码解释

`Book.java` 实体。继承了`BaseEntity`，继承一些基础属性，如：id、createTime、updateTime、deleted...等等

`BookDto.java` 传输对象。继承了`EntityDto`，也是继承了一些基础属性

`BookQuery.java` 查询参数。继承了`EntityQuery`，继承了基础的查询基础属性，排序，分页等

`BookController.java` controller层。继承了`EntityController`，里面提供了6个基础接口，包括增删改查、列表、分页查询

`BookService.java` service层。继承了`EntityService`，定义了一些基础接口

`BookServiceImpl.java` service实现。默认实现了service层定义的接口

`BookMapper.java` mapper层。继承了MyBatis-Plus的BaseMapper

`BookMapper.xml` mapper对应的xml文件

#### 注解说明

- `@Query` 该注解标识该支持字段查询。`column`-对应的数据库字段，如果为空则取属性名转下划线作为查询字段名。`expression`标识该字段的查询方式，目前支持精确、模糊、范围、大于、小于等等查询。注意，当`expression = ExpressionEnum.BETWEEN`即范围查询时，`column`不能为空，且`column`的值必须成对存在，否则无法启动。如下配置表示在`create_time`字段进行范围查询
    ```java
    // 精确查询 等同于 @Query(column = "status", expression = ExpressionEnum.EQ)
    @Query
    @ApiModelProperty("状态 0-维护中，1-正常，2-借出")
    private Integer status;
    ```
    ```java
    // 模糊查询 等同于 @Query(column = "name", expression = ExpressionEnum.LIKE)
    @Query(expression = ExpressionEnum.LIKE)
    @ApiModelProperty("书名")
    private String name;
    ```
    ```java
    // 范围查询 create_time
    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("开始时间")
    private Date startDate;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("结束时间")
    private Date endDate;
    ```

- `@EntityField` 该注解标识该字段的对数据库的操作。如下配置, 表示该字段可以新增，但不可编辑字段。
    ```java
    @EntityField(insert = true, update = false)
    @ApiModelProperty("收货人姓名")
    private String receiverName;
    ```

#### 参数校验组

- `CreateGroup.class` 默认提供的添加接口会校验该分组
- `UpdateGroup.clas` 默认提供的编辑接口会校验该分组
    ```java
    @NotBlank(message = "收货人不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty("收货人姓名")
    private String receiverName;
    ```
#### 接口代码流程

### 接口代码流程

#### 添加或更新

1. 进入`beforeSaveOrUpdateHandler(dto)`方法，可以对'增改'接口参数进行统一处理
2. '增'接口进入`beforeSaveHandler(dto)`，'改'接口进入`beforeUpdateHandler(dto)`，分别对参数进行处理
3. 复制带有`XxxDto`中带有`@EntityField`属性给实体
4. 执行更新`updateById(entity)`或插入`save(entity)`操作
5. 执行`afterSaveHandler(entity)`方法，可在此进行添加或更新的后置处理

#### 单条查询

1. 执行`getById(id)`
2. 执行`afterQueryHandler(entity, queryHandler)`，根据不同的需求使用不同的处理器对响应的数据进行处理，返回`dto`

#### 列表、分页查询

1. 进入`handleQueryWrapper(query)`方法，处理查询的信息，分页信息等
2. 执行`page(queryWrapper)`查询或`list(queryWrapper)`
3. 执行`afterQueryHandler(list, queryHandler)`，根据不同的需求使用不同的处理器对响应的数据进行处理

#### 删除

1. 执行`removeById(id)`
2. 执行`afterDeleteHandler(entity)` 可在此处做删除的后置操作