<#assign dbTime = "now()">
<#if dbType=="SQLServer">
    <#assign dbTime = "getDate()">
</#if>

-- 初始化菜单
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, creator, create_time, updater, update_time) VALUES (1, '${tableComment!}', '${moduleName}/${functionName}/index', NULL, 0, 0, 'icon-menu', 0, 10000, ${dbTime}, 10000, ${dbTime});

INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, creator, create_time, updater, update_time) VALUES ((SELECT id from (SELECT id from sys_menu where name = '${tableComment!}') AS temp), '查看', '', '${moduleName}:${functionName}:page', 1, 0, '', 0, 10000, ${dbTime}, 10000, ${dbTime});
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, creator, create_time, updater, update_time) VALUES ((SELECT id from (SELECT id from sys_menu where name = '${tableComment!}') AS temp), '新增', '', '${moduleName}:${functionName}:save', 1, 0, '', 1, 10000, ${dbTime}, 10000, ${dbTime});
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, creator, create_time, updater, update_time) VALUES ((SELECT id from (SELECT id from sys_menu where name = '${tableComment!}') AS temp), '修改', '', '${moduleName}:${functionName}:update,${moduleName}:${functionName}:info', 1, 0, '', 2, 10000, ${dbTime}, 10000, ${dbTime});
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, creator, create_time, updater, update_time) VALUES ((SELECT id from (SELECT id from sys_menu where name = '${tableComment!}') AS temp), '删除', '', '${moduleName}:${functionName}:delete', 1, 0, '', 3, 10000, ${dbTime}, 10000, ${dbTime});
