INSERT INTO `permission`(`collection`, `action`, `permissions`, `validation`, `presets`, `fields`, `authority`) VALUES 
-- -----------------------
-- 所有读权限(新增角色默认权限)
-- -----------------------
('attachment', 'read', '{}', '{}', NULL, '*', NULL),
('authority', 'read', '{}', '{}', NULL, '*', NULL),
('brand', 'read', '{}', '{}', NULL, '*', NULL),
('contact', 'read', '{}', '{}', NULL, '*', NULL),
('contract', 'read', '{}', '{}', NULL, '*', NULL),
('cost', 'read', '{}', '{}', NULL, '*', NULL),
('cost_type', 'read', '{}', '{}', NULL, '*', NULL),
('customer', 'read', '{}', '{}', NULL, '*', NULL),
('department', 'read', '{}', '{}', NULL, '*', NULL),
('equipment', 'read', '{}', '{}', NULL, '*', NULL),
('exception', 'read', '{}', '{}', NULL, '*', NULL),
('exception_copy', 'read', '{}', '{}', NULL, '*', NULL),
('exception_order', 'read', '{}', '{}', NULL, '*', NULL),
('exception_owner', 'read', '{}', '{}', NULL, '*', NULL),
('exception_type', 'read', '{}', '{}', NULL, '*', NULL),
('model', 'read', '{}', '{}', NULL, '*', NULL),
('operation', 'read', '{}', '{}', NULL, '*', NULL),
('order', 'read', '{}', '{}', NULL, '*', NULL),
('package', 'read', '{}', '{}', NULL, '*', NULL),
('package_service', 'read', '{}', '{}', NULL, '*', NULL),
('permission', 'read', '{}', '{}', NULL, '*', NULL),
('quotation', 'read', '{}', '{}', NULL, '*', NULL),
('quotation_service', 'read', '{}', '{}', NULL, '*', NULL),
('receive', 'read', '{}', '{}', NULL, '*', NULL),
('role', 'read', '{}', '{}', NULL, '*', NULL),
('role_authority', 'read', '{}', '{}', NULL, '*', NULL),
('service', 'read', '{}', '{}', NULL, '*', NULL),
('settlement_contract', 'read', '{}', '{}', NULL, '*', NULL),
('storage', 'read', '{}', '{}', NULL, '*', NULL),
('user', 'read', '{}', '{}', NULL, '*', NULL),
('directus_users', 'read', '{}', '{}', NULL, '*', NULL),
('directus_roles', 'read', '{}', '{}', NULL, '*', NULL),
('directus_permissions', 'read', '{}', '{}', NULL, '*', NULL),
('directus_files', 'read', '{}', '{}', NULL, '*', NULL),
-- -----------------------
-- 1. 销售报价表
-- -----------------------
('quotation', 'create', '{}', '{}', NULL, '*', 1),
('quotation', 'update', '{}', '{}', NULL, '*', 1),
('qoutation', 'delete', '{}', '{}', NULL, '*', 1),
('quotation_service', 'create', '{}', '{}', NULL, '*', 1),
('quotation_service', 'update', '{}', '{}', NULL, '*', 1),
('quotation_service', 'delete', '{}', '{}', NULL, '*', 1),
('directus_files', 'create', '{}', '{}', NULL, '*', 1),
('directus_files', 'delete', '{}', '{}', NULL, '*', 1),
('attachment', 'create', '{}', '{}', NULL, '*', 1),
('attachment', 'delete', '{}', '{}', NULL, '*', 1),
-- -----------------------
-- 2. 客户新增、品牌(客户基本信息，联系人信息，发票信息)(涵盖操作记录)
-- -----------------------
('customer', 'create', '{}', '{}', NULL, '*', 2),
('brand', 'create', '{}', '{}', NULL, '*', 2),
('contact', 'create', '{}', '{}', NULL, '*', 2),
('operation', 'create', '{}', '{}', NULL, '*', 2),
-- -----------------------
-- 3. 客户编辑(客户基本信息，联系人信息，发票信息，品牌)(涵盖操作记录)
-- -----------------------
('customer', 'update', '{}', '{}', NULL, 'name,source,type,business,invoice,parent,seller,creator,editor,create_time,last_update_time,invoice_type,identity,account,mobile,bank,post_address,register_address', 3),
('brand', 'update', '{}', '{}', NULL, '*', 3),
('contact', 'create', '{}', '{}', NULL, '*', 3),
('contact', 'update', '{}', '{}', NULL, '*', 3),
('contact', 'delete', '{}', '{}', NULL, '*', 3),
('operation', 'create', '{}', '{}', NULL, '*', 3),
-- -----------------------
-- 4. 客户寻回&流失("status")(涵盖操作记录)
-- -----------------------
('customer', 'update', '{}', '{}', NULL, 'status', 4),
('operation', 'create', '{}', '{}', NULL, '*', 4),
-- -----------------------
-- 5. 器材管理新增、编辑
-- -----------------------
('equipment', 'create', '{}', '{}', NULL, '*', 5),
('equipment', 'update', '{}', '{}', NULL, '*', 5),
-- -----------------------
-- 6. 器材管理删除
-- -----------------------
('equipment', 'delete', '{}', '{}', NULL, '*', 6),
-- -----------------------
-- 7. 型号管理新增、编辑、禁用&启用
-- -----------------------
('model', 'create', '{}', '{}', NULL, '*', 7),
('model', 'update', '{}', '{}', NULL, '*', 7),
-- -----------------------
-- 8. 仓库管理新增、编辑
-- -----------------------
('storage', 'create', '{}', '{}', NULL, '*', 8),
('storage', 'update', '{}', '{}', NULL, '*', 8),
-- -----------------------
-- 9. 产品套餐新增、编辑
-- -----------------------
('package', 'create', '{}', '{}', NULL, '*', 9),
('package', 'update', '{}', '{}', NULL, '*', 9),
('package_service', 'create', '{}', '{}', NULL, '*', 9),
('package_service', 'update', '{}', '{}', NULL, '*', 9),
('package_service', 'delete', '{}', '{}', NULL, '*', 9),
-- -----------------------
-- 10. 产品套餐删除(涵盖套餐服务删除)
-- -----------------------
('package', 'delete', '{}', '{}', NULL, '*', 10),
('package_service', 'delete', '{}', '{}', NULL, '*', 10),
-- -----------------------
-- 11. 服务新增、新增子服务、编辑、禁用&启用
-- -----------------------
('service', 'create', '{}', '{}', NULL, '*', 11),
('service', 'update', '{}', '{}', NULL, '*', 11),
-- -----------------------
-- 12. 费用新增、编辑、禁用&启用
-- -----------------------
('cost', 'create', '{}', '{}', NULL, '*', 12),
('cost', 'update', '{}', '{}', NULL, '*', 12),
-- -----------------------
-- 13. 费用类型新增、编辑、禁用&启用
-- -----------------------
('cost_type', 'create', '{}', '{}', NULL, '*', 13),
('cost_type', 'update', '{}', '{}', NULL, '*', 13),
-- -----------------------
-- 14. 异常新增、编辑(涵盖异常关联表增删，操作记录)
-- -----------------------
('attachment', "create", '{}', '{}', NULL, '*', 14),
('exception', 'create', '{}', '{}', NULL, '*', 14),
('exception', 'update', '{}', '{}', NULL, 'type,date,orders,department,owners,copies,judge,complaint,attachments,selfCheck,narrative,estimatedLoss,estimatedLossCondition,level', 14),
('exception_order', 'create', '{}', '{}', NULL, '*', 14),
('exception_order', 'delete', '{}', '{}', NULL, '*', 14),
('exception_owner', 'create', '{}', '{}', NULL, '*', 14),
('exception_owner', 'delete', '{}', '{}', NULL, '*', 14),
('exception_copy', 'create', '{}', '{}', NULL, '*', 14),
('exception_copy', 'delete', '{}', '{}', NULL, '*', 14),
-- -----------------------
-- 15. 异常处理(涵盖操作记录)
-- -----------------------
('exception', 'update', '{}', '{}', NULL, 'status', 15),
('operation', 'create', '{}', '{}', NULL, '*', 15),
-- -----------------------
-- 16. 异常删除
-- -----------------------
('exception', 'delete', '{}', '{}', NULL, '*', 16),
('operation', 'delete', '{}', '{}', NULL, '*', 16),
-- -----------------------
-- 17. 异常类型新增、编辑、禁用&启用
-- -----------------------
('exception_type', 'create', '{}', '{}', NULL, '*', 17),
('exception_type', 'update', '{}', '{}', NULL, '*', 17),
-- -----------------------
-- 18. 合同录入新增、关联订单、新增结算
-- -----------------------
('contract', 'create', '{}', '{}', NULL, '*', 18),
('settlement_contract', 'create', '{}', '{}', NULL, '*', 18),
('operation', 'create', '{}', '{}', NULL, '*', 18),
-- -----------------------
-- 19. 合同管理审核
-- -----------------------
('contract', 'update', '{}', '{}', NULL, 'status', 19),
('settlement_contract', 'update', '{}', '{}', NULL, 'status', 19),
('operation', 'create', '{}', '{}', NULL, '*', 19),
-- -----------------------
-- 20. 合同管理收款
-- -----------------------
('receive', 'create', '{}', '{}', NULL, '*', 20),
-- -----------------------
-- 21. 合同管理收到纸质合同(涵盖附件attachment)
-- -----------------------
('contract', 'update', '{}', '{}', NULL, 'paper_date', 21),
('directus_files', 'create', '{}', '{}', NULL, '*', 21),
('directus_files', 'delete', '{}', '{}', NULL, '*', 21),
('attachment', 'create', '{}', '{}', NULL, '*', 21),
('attachment', 'delete', '{}', '{}', NULL, '*', 21),
-- -----------------------
-- 22. 组织管理同步组织
-- -----------------------
('department', 'create', '{}', '{}', NULL, '*', 22),
('department', 'update', '{}', '{}', NULL, '*', 22),
-- -----------------------
-- 23. 组织管理删除
-- -----------------------
('department', 'delete', '{}', '{}', NULL, '*', 23),
-- -----------------------
-- 24. 角色管理新增、编辑
-- -----------------------
('directus_roles', 'create', '{}', '{}', NULL, '*', 24),
('directus_roles', 'update', '{}', '{}', NULL, '*', 24),
('directus_permissions', 'create', '{}', '{}', NULL, '*', 24),
('directus_permissions', 'delete', '{}', '{}', NULL, '*', 24),
('role', 'create', '{}', '{}', NULL, '*', 24),
('role', 'update', '{}', '{}', NULL, '*', 24),
('role_authority', 'create', '{}', '{}', NULL, '*', 24),
('role_authority', 'update', '{}', '{}', NULL, '*', 24),
('role_authority', 'delete', '{}', '{}', NULL, '*', 24),
-- -----------------------
-- 25. 角色管理删除
-- -----------------------
('directus_roles', 'delete', '{}', '{}', NULL, '*', 25),
('directus_permissions', 'create', '{}', '{}', NULL, '*', 25),
('role', 'delete', '{}', '{}', NULL, '*', 25),
('role_authority', 'update', '{}', '{}', NULL, '*', 24),
('roles_authority', 'delete', '{}', '{}', NULL, '*', 25),
-- -----------------------
-- 26. 人员管理同步账号、禁用&启用
-- -----------------------
('directus_users', 'create', '{}', '{}', NULL, '*', 26),
('user', 'create', '{}', '{}', NULL, '*', 26),
('user', 'update', '{}', '{}', NULL, 'name,user_id,directus_user,directus_password,avatar,email,mobile,internal_position,external_position,lead_departments,source,active,creator,editor,create_time,last_update_time,departments', 26),
('user_department', 'create', '{}', '{}', NULL, '*', 26),
('user_department', 'delete', '{}', '{}', NULL, '*', 26),
-- -----------------------
-- 27. 人员管理编辑
-- -----------------------
('user', 'update', '{}', '{}', NULL, 'employee_id,remark', 27),
-- -----------------------
-- 28. 人员管理授权、批量授权
-- -----------------------
('directus_users', 'update', '{}', '{}', NULL, 'role', 28),
('user', 'update', '{}', '{}', NULL, 'role,auth', 28);