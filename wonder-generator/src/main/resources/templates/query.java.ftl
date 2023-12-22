package ${package.Other}.${entity};

<#list table.importPackages as pkg>
    <#if pkg != "com.baomidou.mybatisplus.annotation.TableField" && pkg != "com.baomidou.mybatisplus.annotation.TableName" &&  pkg != "com.gitee.whzzone.common.base.pojo.entity.BaseEntity">
import ${pkg};
    </#if>
</#list>
import com.gitee.whzzone.common.base.pojo.query.EntityQuery;
import com.gitee.whzzone.common.annotation.Query;
import com.gitee.whzzone.common.enums.ExpressionEnum;
<#if swagger>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
    <#if chainModel>
import lombok.experimental.Accessors;
    </#if>
</#if>

import java.util.Date;

/**
* ${table.comment!}
* @author Create by ${author} at ${date}
*/
<#if entityLombokModel>
@Getter
@Setter
@ToString
    <#if chainModel>
@Accessors(chain = true)
    </#if>
</#if>
<#if swagger>
@ApiModel(value = "${entity}Query对象", description = "${table.comment!}")
</#if>
public class ${entity}Query extends EntityQuery {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field != "createTime" && field != "createBy" && field != "updateTime" && field != "updateBy" && field != "deleted">

    @Query
    @ApiModelProperty("${field.comment}")
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("开始日期")
    private Date beginDate;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("结束日期")
    private Date endDate;
<#------------  END 字段循环遍历  ---------->
<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

    <#if chainModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if chainModel>
        return this;
        </#if>
    }
    </#list>
</#if>

<#if entityColumnConstant>
    <#list table.fields as field>
    public static final String ${field.name?upper_case} = "${field.name}";

    </#list>
</#if>
<#if activeRecord>
    @Override
    public Serializable pkVal() {
    <#if keyPropertyName??>
        return this.${keyPropertyName};
    <#else>
        return null;
    </#if>
    }

</#if>
<#if !entityLombokModel>
    @Override
    public String toString() {
        return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
        "}";
    }
</#if>
}
