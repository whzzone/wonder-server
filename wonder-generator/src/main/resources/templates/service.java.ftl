package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import ${package.Other}.${entity}.${entity}Query;
import ${package.Other}.${entity}.${entity}Dto;

/**
* ${table.comment!} 服务类
* @author Create by ${author} at ${date}
*/
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}, ${entity}Dto, ${entity}Query> {

}
</#if>
