package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Other}.${entity}.${entity}Query;
import ${package.Other}.${entity}.${entity}DTO;
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
* ${table.comment!} 服务实现类
* @author Create by ${author} at ${date}
*/
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}, ${entity}DTO, ${entity}Query> implements ${table.serviceName} {

}
</#if>
