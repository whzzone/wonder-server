# wonder-server

### admin/123456

> 启动报错：java: 服务配置文件不正确, 或构造处理程序对象javax.annotation.processing.Processor: Provider com.gitee.whzzone.processor.QueryProcessor not found时抛出异常错误
>
> 解决办法：在wonder-server模块依次执行mvn clean、mvn install即可
> 
> 对wonder-processor代码修改后都要执行以上方法

#### 前端 https://gitee.com/whzzone/wonder-web

#### 后端 https://gitee.com/whzzone/wonder-server

#### 这是一个基础的RBAC权限管理系统

优点：传统的5种权限：全部数据权限、本部门数据权限、本部门及下属部门数据、仅本人数据、自定义数据权限，其实再怎么自定义也是针对人和部门的纬度来说的，如果需求是：角色A只能订单表的销售数量>=100的订单，角色B只能订单表的销售数量<100的订单，恐怕实现不了。本项目针对这种需求就设计得更灵活了一点，仅需要针对不同的接口可视化添加一些配置即可以针对数据库表不同字段来控制数据权限。

缺点：如果是控制比较复杂的查询，需要在项目代码中预先写好相关的方法来提供使用。执行时间慢那么一点，但也只是对有限制的接口来说，还是可以接受的。

#### 当前数据权限思路
@DataScope`注解使用在方法上： 执行SQL前进行拦截，查询该账号拥有的角色List，是否关联有的数据规则，解析拼接SQL。没有关联则返回无数据。
    
```java
// mapper 建议使用在mapper方法接口上
@Mapper 
public interface OrderMapper extends BaseMapper<Order> {
    
    @DataScope("order-list")
    @Override
    List<Order> selectList(@Param(Constants.WRAPPER) Wrapper<Order> queryWrapper);
    
}


// service 用在service层方法上时，要注意调用的方法是否是本类的方法
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
---

#### 问题
1. 拥有多个角色的时候，怎么知道该取哪条规则？ 
    - 方案一： 角色表（也可以认为是用户组）增加一个`优先级`字段，每个用户拥有的角色中，最大的优先级只能同时有一个。取规则的时候就取优先级最大的角色关联的规则去处理即可。
    - 方案二：角色设计成父子继承关系（类似于RBAC1模型），父角色拥有子角色所有的权限，也就是用户所拥有的角色中优先级最大的就是最上层角色，取规则的时候就取最上层角色关联的规则去处理即可。
    - 方案三：目前使用。通过前端切换角色同时前端在无需重新登录的下根据所选的角色来重新生成新的菜单和路由，请求时`header`传递所选角色`id`，部门同理
    ![img.png](/image/20230804174232478.png)
~~2. 怎么知道规则中的字段对应的是哪张表，通用字段可还行，比如`id`、`update_by`、`create_by`、`update_time`、`create_time`等每个表都会存在的字段。但是如果遇到某些每个表特有字段例如订单表有`number`、汽车表有`color`字段时，怎么处理？~~
    ~~- 方案一：在@DataScope注解中新增一个字段`tableName`，用来标识该条规则作用于哪个表，为空就是作用于所有表。例如`@DataScope(value="sn1", tableName="order")`将作用于`order`表。（这样的话，`sn1`的存在是不是多余了，直接查询用户优先级最高的角色有没有关联`order`这张表的规则限制。）~~

3. 如果想限制同一张表的多个字段怎么办？比如某条规则限制`order`表查询付款金额大于100元 && 已经完成的订单时怎么办？
    - 方案一：优先考虑提供类型为`值`的情况是否满足当前需求。如需要限制两个及以上字段时，使用提供类型为`方法`来处理，此时配置记录中字段`column_name`为`id`，配置对应的无参或有参方法，返回`idList`去`IN`，就能满足。例如问题中可一执行一个方法返回 付款金额大于100元 && 已经完成的订单 的`idList`去`order`表拼接`id IN( idList )` 就能满足

---

#### 接下来的需求

- [ ] `column_name`可以设计成分号隔开的类型，同时新增一个连接条件

    | id | column_name  | column_splice_type | value  | expression | 其他属性不变 |
    |----|--------------|--------------------|--------|------------|--------|
    |    | number;color | AND;OR             | 100;黑色 | GE;EQ      |        |
    
    这条记录代表着需要拼接SQL语句：xxx AND number >= 100 OR color = '黑色' 
2. [x] 系统字典
3. [x] 系统日志
4. [x] 接口访问频率限制

---

#### Bug && Todo
1. [x] 仅当update菜单时报错：`java.lang.NoSuchFieldException: id`，其他接口为出现此情况
2. [ ] 在菜单页面编辑后，如何局部刷新，现在是整个页面刷新，影响体验
3. [ ] 新增菜单是`按钮`时，后端在处理时设置`inFrame = true`，因为按钮也可以表示打开一个新的tab页，（是不是一定是框架内打开，待考虑）
4. [x] 代码生成，包括`controller`、`service`、`mapper`...
5. [x] `EntityQuery`查询时，怎么知道某个字段的匹配方法是什么？ eq? like? ne? 方案1：自定义注解@Equal、@Like、@In，然后反射读取做对应处理
6. [x] 分页，列表查询，除了`EntityQuery`，还有另外一种方案：通过自定义注解`@Query`标记`Dto`对象中可以查询的字段，`page`接口、`list`接口读取这个注解就可以了
7. [ ] `between`的值可以用特定符号分开 1,9。in同理
8. [x] 对于使用了`@Query`直接且`expression`为`BETWEEN`时的由运行时校验改成编译时校验，可提高执行速度。目前时运行时校验，其实只要在编译时校验一次够了。
    ```java
    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("开始时间")
    private Date startDate;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("结束时间")
    private Date endDate;
    ```