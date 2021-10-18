/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 172.17.0.1:3306
 Source Schema         : topland

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 18/10/2021 13:41:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `contract` bigint DEFAULT NULL,
  `settlement` bigint DEFAULT NULL,
  `exception` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `attachment_contract_foreign` (`contract`),
  KEY `attachment_exception_foreign` (`exception`),
  KEY `attachment_file_foreign` (`file`),
  KEY `attachment_settlement_foreign` (`settlement`),
  CONSTRAINT `attachment_contract_foreign` FOREIGN KEY (`contract`) REFERENCES `contract` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `attachment_exception_foreign` FOREIGN KEY (`exception`) REFERENCES `exception` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `attachment_file_foreign` FOREIGN KEY (`file`) REFERENCES `directus_files` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `customer` bigint DEFAULT NULL,
  `seller` bigint NOT NULL,
  `producer` bigint DEFAULT NULL,
  `business` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `brand_name_unique` (`name`),
  KEY `brand_creator_foreign` (`creator`),
  KEY `brand_editor_foreign` (`editor`),
  KEY `brand_producer_foreign` (`producer`),
  KEY `brand_seller_foreign` (`seller`),
  KEY `brand_customer_foreign` (`customer`),
  CONSTRAINT `brand_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `brand_customer_foreign` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `brand_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `brand_producer_foreign` FOREIGN KEY (`producer`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `brand_seller_foreign` FOREIGN KEY (`seller`) REFERENCES `user` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `position` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `department` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `remark` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `customer` bigint DEFAULT NULL,
  `brand` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `contact_brand_foreign` (`brand`),
  KEY `contact_customer_foreign` (`customer`),
  CONSTRAINT `contact_brand_foreign` FOREIGN KEY (`brand`) REFERENCES `brand` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `contact_customer_foreign` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `identity` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `contract_date` date NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `paper_date` date DEFAULT NULL,
  `receivable` decimal(11,2) DEFAULT NULL,
  `guarantee` decimal(11,2) DEFAULT NULL,
  `margin` decimal(11,2) DEFAULT NULL,
  `remark` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `brand` bigint DEFAULT NULL,
  `customer` bigint NOT NULL,
  `order` bigint DEFAULT NULL,
  `seller` bigint NOT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `contract_identity_unique` (`identity`),
  KEY `contract_editor_foreign` (`editor`),
  KEY `contract_seller_foreign` (`seller`),
  KEY `contract_customer_foreign` (`customer`),
  KEY `contract_order_foreign` (`order`),
  KEY `contract_creator_foreign` (`creator`),
  KEY `contract_brand_foreign` (`brand`),
  CONSTRAINT `contract_brand_foreign` FOREIGN KEY (`brand`) REFERENCES `brand` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `contract_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `contract_customer_foreign` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `contract_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `contract_order_foreign` FOREIGN KEY (`order`) REFERENCES `order` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `contract_seller_foreign` FOREIGN KEY (`seller`) REFERENCES `user` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for cost
-- ----------------------------
DROP TABLE IF EXISTS `cost`;
CREATE TABLE `cost` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `active` bit(1) DEFAULT b'1',
  `type` bigint NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cost_name_unique` (`name`),
  KEY `cost_type_foreign` (`type`),
  CONSTRAINT `cost_type_foreign` FOREIGN KEY (`type`) REFERENCES `cost_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for cost_type
-- ----------------------------
DROP TABLE IF EXISTS `cost_type`;
CREATE TABLE `cost_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT b'1',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `remark` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cost_type_name_unique` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `source` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `business` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `invoice` bigint DEFAULT NULL,
  `parent` bigint DEFAULT NULL,
  `seller` bigint DEFAULT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `invoice_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `identity` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `bank` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `post_address` varchar(100) NOT NULL,
  `register_address` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `customer_name_unique` (`name`),
  KEY `customer_editor_foreign` (`editor`),
  KEY `customer_creator_foreign` (`creator`),
  KEY `customer_parent_foreign` (`parent`),
  KEY `customer_seller_foreign` (`seller`),
  CONSTRAINT `customer_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `customer_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `customer_parent_foreign` FOREIGN KEY (`parent`) REFERENCES `customer` (`id`) ON DELETE CASCADE,
  CONSTRAINT `customer_seller_foreign` FOREIGN KEY (`seller`) REFERENCES `user` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `source` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dept_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `parent_dept_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sort` bigint DEFAULT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `department_creator_foreign` (`creator`),
  KEY `department_editor_foreign` (`editor`),
  CONSTRAINT `department_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `department_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_activity
-- ----------------------------
DROP TABLE IF EXISTS `directus_activity`;
CREATE TABLE `directus_activity` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `action` varchar(45) NOT NULL,
  `user` char(36) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(50) NOT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `collection` varchar(64) NOT NULL,
  `item` varchar(255) NOT NULL,
  `comment` text,
  PRIMARY KEY (`id`),
  KEY `directus_activity_collection_foreign` (`collection`)
) ENGINE=InnoDB AUTO_INCREMENT=2793 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_collections
-- ----------------------------
DROP TABLE IF EXISTS `directus_collections`;
CREATE TABLE `directus_collections` (
  `collection` varchar(64) NOT NULL,
  `icon` varchar(30) DEFAULT NULL,
  `note` text,
  `display_template` varchar(255) DEFAULT NULL,
  `hidden` tinyint(1) NOT NULL DEFAULT '0',
  `singleton` tinyint(1) NOT NULL DEFAULT '0',
  `translations` json DEFAULT NULL,
  `archive_field` varchar(64) DEFAULT NULL,
  `archive_app_filter` tinyint(1) NOT NULL DEFAULT '1',
  `archive_value` varchar(255) DEFAULT NULL,
  `unarchive_value` varchar(255) DEFAULT NULL,
  `sort_field` varchar(64) DEFAULT NULL,
  `accountability` varchar(255) DEFAULT 'all',
  `color` varchar(255) DEFAULT NULL,
  `item_duplication_fields` json DEFAULT NULL,
  PRIMARY KEY (`collection`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_dashboards
-- ----------------------------
DROP TABLE IF EXISTS `directus_dashboards`;
CREATE TABLE `directus_dashboards` (
  `id` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `icon` varchar(30) NOT NULL DEFAULT 'dashboard',
  `note` text,
  `date_created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_created` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `directus_dashboards_user_created_foreign` (`user_created`),
  CONSTRAINT `directus_dashboards_user_created_foreign` FOREIGN KEY (`user_created`) REFERENCES `directus_users` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_fields
-- ----------------------------
DROP TABLE IF EXISTS `directus_fields`;
CREATE TABLE `directus_fields` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `collection` varchar(64) NOT NULL,
  `field` varchar(64) NOT NULL,
  `special` varchar(64) DEFAULT NULL,
  `interface` varchar(64) DEFAULT NULL,
  `options` json DEFAULT NULL,
  `display` varchar(64) DEFAULT NULL,
  `display_options` json DEFAULT NULL,
  `readonly` tinyint(1) NOT NULL DEFAULT '0',
  `hidden` tinyint(1) NOT NULL DEFAULT '0',
  `sort` int unsigned DEFAULT NULL,
  `width` varchar(30) DEFAULT 'full',
  `translations` json DEFAULT NULL,
  `note` text,
  `conditions` json DEFAULT NULL,
  `required` tinyint(1) DEFAULT '0',
  `group` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `directus_fields_collection_foreign` (`collection`)
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_files
-- ----------------------------
DROP TABLE IF EXISTS `directus_files`;
CREATE TABLE `directus_files` (
  `id` char(36) NOT NULL,
  `storage` varchar(255) NOT NULL,
  `filename_disk` varchar(255) DEFAULT NULL,
  `filename_download` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `folder` char(36) DEFAULT NULL,
  `uploaded_by` char(36) DEFAULT NULL,
  `uploaded_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` char(36) DEFAULT NULL,
  `modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `charset` varchar(50) DEFAULT NULL,
  `filesize` bigint DEFAULT NULL,
  `width` int unsigned DEFAULT NULL,
  `height` int unsigned DEFAULT NULL,
  `duration` int unsigned DEFAULT NULL,
  `embed` varchar(200) DEFAULT NULL,
  `description` text,
  `location` text,
  `tags` text,
  `metadata` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `directus_files_uploaded_by_foreign` (`uploaded_by`),
  KEY `directus_files_modified_by_foreign` (`modified_by`),
  KEY `directus_files_folder_foreign` (`folder`),
  CONSTRAINT `directus_files_folder_foreign` FOREIGN KEY (`folder`) REFERENCES `directus_folders` (`id`) ON DELETE SET NULL,
  CONSTRAINT `directus_files_modified_by_foreign` FOREIGN KEY (`modified_by`) REFERENCES `directus_users` (`id`),
  CONSTRAINT `directus_files_uploaded_by_foreign` FOREIGN KEY (`uploaded_by`) REFERENCES `directus_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_folders
-- ----------------------------
DROP TABLE IF EXISTS `directus_folders`;
CREATE TABLE `directus_folders` (
  `id` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `directus_folders_parent_foreign` (`parent`),
  CONSTRAINT `directus_folders_parent_foreign` FOREIGN KEY (`parent`) REFERENCES `directus_folders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_migrations
-- ----------------------------
DROP TABLE IF EXISTS `directus_migrations`;
CREATE TABLE `directus_migrations` (
  `version` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_panels
-- ----------------------------
DROP TABLE IF EXISTS `directus_panels`;
CREATE TABLE `directus_panels` (
  `id` char(36) NOT NULL,
  `dashboard` char(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `icon` varchar(30) DEFAULT 'insert_chart',
  `color` varchar(10) DEFAULT NULL,
  `show_header` tinyint(1) NOT NULL DEFAULT '0',
  `note` text,
  `type` varchar(255) NOT NULL,
  `position_x` int NOT NULL,
  `position_y` int NOT NULL,
  `width` int NOT NULL,
  `height` int NOT NULL,
  `options` json DEFAULT NULL,
  `date_created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_created` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `directus_panels_dashboard_foreign` (`dashboard`),
  KEY `directus_panels_user_created_foreign` (`user_created`),
  CONSTRAINT `directus_panels_dashboard_foreign` FOREIGN KEY (`dashboard`) REFERENCES `directus_dashboards` (`id`) ON DELETE CASCADE,
  CONSTRAINT `directus_panels_user_created_foreign` FOREIGN KEY (`user_created`) REFERENCES `directus_users` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_permissions
-- ----------------------------
DROP TABLE IF EXISTS `directus_permissions`;
CREATE TABLE `directus_permissions` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `role` char(36) DEFAULT NULL,
  `collection` varchar(64) NOT NULL,
  `action` varchar(10) NOT NULL,
  `permissions` json DEFAULT NULL,
  `validation` json DEFAULT NULL,
  `presets` json DEFAULT NULL,
  `fields` text,
  PRIMARY KEY (`id`),
  KEY `directus_permissions_collection_foreign` (`collection`),
  KEY `directus_permissions_role_foreign` (`role`),
  CONSTRAINT `directus_permissions_role_foreign` FOREIGN KEY (`role`) REFERENCES `directus_roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_presets
-- ----------------------------
DROP TABLE IF EXISTS `directus_presets`;
CREATE TABLE `directus_presets` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `bookmark` varchar(255) DEFAULT NULL,
  `user` char(36) DEFAULT NULL,
  `role` char(36) DEFAULT NULL,
  `collection` varchar(64) DEFAULT NULL,
  `search` varchar(100) DEFAULT NULL,
  `layout` varchar(100) DEFAULT 'tabular',
  `layout_query` json DEFAULT NULL,
  `layout_options` json DEFAULT NULL,
  `refresh_interval` int DEFAULT NULL,
  `filter` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `directus_presets_collection_foreign` (`collection`),
  KEY `directus_presets_user_foreign` (`user`),
  KEY `directus_presets_role_foreign` (`role`),
  CONSTRAINT `directus_presets_role_foreign` FOREIGN KEY (`role`) REFERENCES `directus_roles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `directus_presets_user_foreign` FOREIGN KEY (`user`) REFERENCES `directus_users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_relations
-- ----------------------------
DROP TABLE IF EXISTS `directus_relations`;
CREATE TABLE `directus_relations` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `many_collection` varchar(64) NOT NULL,
  `many_field` varchar(64) NOT NULL,
  `one_collection` varchar(64) DEFAULT NULL,
  `one_field` varchar(64) DEFAULT NULL,
  `one_collection_field` varchar(64) DEFAULT NULL,
  `one_allowed_collections` text,
  `junction_field` varchar(64) DEFAULT NULL,
  `sort_field` varchar(64) DEFAULT NULL,
  `one_deselect_action` varchar(255) NOT NULL DEFAULT 'nullify',
  PRIMARY KEY (`id`),
  KEY `directus_relations_many_collection_foreign` (`many_collection`),
  KEY `directus_relations_one_collection_foreign` (`one_collection`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_revisions
-- ----------------------------
DROP TABLE IF EXISTS `directus_revisions`;
CREATE TABLE `directus_revisions` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `activity` int unsigned NOT NULL,
  `collection` varchar(64) NOT NULL,
  `item` varchar(255) NOT NULL,
  `data` json DEFAULT NULL,
  `delta` json DEFAULT NULL,
  `parent` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `directus_revisions_collection_foreign` (`collection`),
  KEY `directus_revisions_parent_foreign` (`parent`),
  KEY `directus_revisions_activity_foreign` (`activity`),
  CONSTRAINT `directus_revisions_activity_foreign` FOREIGN KEY (`activity`) REFERENCES `directus_activity` (`id`) ON DELETE CASCADE,
  CONSTRAINT `directus_revisions_parent_foreign` FOREIGN KEY (`parent`) REFERENCES `directus_revisions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1245 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_roles
-- ----------------------------
DROP TABLE IF EXISTS `directus_roles`;
CREATE TABLE `directus_roles` (
  `id` char(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  `icon` varchar(30) NOT NULL DEFAULT 'supervised_user_circle',
  `description` text,
  `ip_access` text,
  `enforce_tfa` tinyint(1) NOT NULL DEFAULT '0',
  `collection_list` json DEFAULT NULL,
  `admin_access` tinyint(1) NOT NULL DEFAULT '0',
  `app_access` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_sessions
-- ----------------------------
DROP TABLE IF EXISTS `directus_sessions`;
CREATE TABLE `directus_sessions` (
  `token` varchar(64) NOT NULL,
  `user` char(36) NOT NULL,
  `expires` timestamp NOT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `data` json DEFAULT NULL,
  PRIMARY KEY (`token`),
  KEY `directus_sessions_user_foreign` (`user`),
  CONSTRAINT `directus_sessions_user_foreign` FOREIGN KEY (`user`) REFERENCES `directus_users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_settings
-- ----------------------------
DROP TABLE IF EXISTS `directus_settings`;
CREATE TABLE `directus_settings` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `project_name` varchar(100) NOT NULL DEFAULT 'Directus',
  `project_url` varchar(255) DEFAULT NULL,
  `project_color` varchar(10) DEFAULT '#00C897',
  `project_logo` char(36) DEFAULT NULL,
  `public_foreground` char(36) DEFAULT NULL,
  `public_background` char(36) DEFAULT NULL,
  `public_note` text,
  `auth_login_attempts` int unsigned DEFAULT '25',
  `auth_password_policy` varchar(100) DEFAULT NULL,
  `storage_asset_transform` varchar(7) DEFAULT 'all',
  `storage_asset_presets` json DEFAULT NULL,
  `custom_css` text,
  `storage_default_folder` char(36) DEFAULT NULL,
  `basemaps` json DEFAULT NULL,
  `mapbox_key` varchar(255) DEFAULT NULL,
  `module_bar` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `directus_settings_project_logo_foreign` (`project_logo`),
  KEY `directus_settings_public_foreground_foreign` (`public_foreground`),
  KEY `directus_settings_public_background_foreign` (`public_background`),
  KEY `directus_settings_storage_default_folder_foreign` (`storage_default_folder`),
  CONSTRAINT `directus_settings_project_logo_foreign` FOREIGN KEY (`project_logo`) REFERENCES `directus_files` (`id`),
  CONSTRAINT `directus_settings_public_background_foreign` FOREIGN KEY (`public_background`) REFERENCES `directus_files` (`id`),
  CONSTRAINT `directus_settings_public_foreground_foreign` FOREIGN KEY (`public_foreground`) REFERENCES `directus_files` (`id`),
  CONSTRAINT `directus_settings_storage_default_folder_foreign` FOREIGN KEY (`storage_default_folder`) REFERENCES `directus_folders` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_users
-- ----------------------------
DROP TABLE IF EXISTS `directus_users`;
CREATE TABLE `directus_users` (
  `id` char(36) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `description` text,
  `tags` json DEFAULT NULL,
  `avatar` char(36) DEFAULT NULL,
  `language` varchar(8) DEFAULT 'en-US',
  `theme` varchar(20) DEFAULT 'auto',
  `tfa_secret` varchar(255) DEFAULT NULL,
  `status` varchar(16) NOT NULL DEFAULT 'active',
  `role` char(36) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `last_access` timestamp NULL DEFAULT NULL,
  `last_page` varchar(255) DEFAULT NULL,
  `provider` varchar(128) NOT NULL DEFAULT 'default',
  `external_identifier` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `directus_users_external_identifier_unique` (`external_identifier`),
  UNIQUE KEY `directus_users_email_unique` (`email`),
  KEY `directus_users_role_foreign` (`role`),
  CONSTRAINT `directus_users_role_foreign` FOREIGN KEY (`role`) REFERENCES `directus_roles` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for directus_webhooks
-- ----------------------------
DROP TABLE IF EXISTS `directus_webhooks`;
CREATE TABLE `directus_webhooks` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `method` varchar(10) NOT NULL DEFAULT 'POST',
  `url` text NOT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'active',
  `data` tinyint(1) NOT NULL DEFAULT '1',
  `actions` varchar(100) NOT NULL,
  `collections` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `identity` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` decimal(11,2) DEFAULT NULL,
  `remark` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `service_time` date NOT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `model` bigint NOT NULL,
  `storage` bigint NOT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `equipment_name_unique` (`name`),
  KEY `FKeimmsje6fqw3yqhp3w7m4338a` (`editor`) USING BTREE,
  KEY `equipment_creator_foreign` (`creator`),
  KEY `equipment_storage_foreign` (`storage`),
  KEY `equipment_model_foreign` (`model`),
  CONSTRAINT `equipment_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `equipment_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `equipment_model_foreign` FOREIGN KEY (`model`) REFERENCES `model` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `equipment_storage_foreign` FOREIGN KEY (`storage`) REFERENCES `storage` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for exception
-- ----------------------------
DROP TABLE IF EXISTS `exception`;
CREATE TABLE `exception` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estimated_loss` decimal(11,2) DEFAULT NULL,
  `estimated_loss_condition` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `complaint` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `narrative` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `self_check` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` bigint NOT NULL,
  `department` bigint DEFAULT NULL,
  `judge` bigint DEFAULT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `attribute` varchar(20) DEFAULT NULL,
  `actual_loss` decimal(11,2) DEFAULT NULL,
  `actual_loss_condition` text,
  `solution` text,
  `optimal_solution` text,
  `optimal` bit(1) DEFAULT NULL,
  `create_date` date NOT NULL,
  `close_date` date DEFAULT NULL,
  `critical` bit(1) DEFAULT b'0',
  `department_source` varchar(50) DEFAULT NULL,
  `resolved` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `exception_type_foreign` (`type`),
  KEY `exception_creator_foreign` (`creator`),
  KEY `exception_editor_foreign` (`editor`),
  KEY `exception_judge_foreign` (`judge`),
  KEY `exception_department_foreign` (`department`),
  CONSTRAINT `exception_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `exception_department_foreign` FOREIGN KEY (`department`) REFERENCES `department` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `exception_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `exception_judge_foreign` FOREIGN KEY (`judge`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `exception_type_foreign` FOREIGN KEY (`type`) REFERENCES `exception_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for exception_copy
-- ----------------------------
DROP TABLE IF EXISTS `exception_copy`;
CREATE TABLE `exception_copy` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `exception_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exception_copy_exception_id_foreign` (`exception_id`),
  KEY `exception_copy_user_id_foreign` (`user_id`),
  CONSTRAINT `exception_copy_exception_id_foreign` FOREIGN KEY (`exception_id`) REFERENCES `exception` (`id`) ON DELETE CASCADE,
  CONSTRAINT `exception_copy_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for exception_order
-- ----------------------------
DROP TABLE IF EXISTS `exception_order`;
CREATE TABLE `exception_order` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `exception_id` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exception_order_exception_id_foreign` (`exception_id`),
  KEY `exception_order_order_id_foreign` (`order_id`),
  CONSTRAINT `exception_order_exception_id_foreign` FOREIGN KEY (`exception_id`) REFERENCES `exception` (`id`) ON DELETE CASCADE,
  CONSTRAINT `exception_order_order_id_foreign` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for exception_owner
-- ----------------------------
DROP TABLE IF EXISTS `exception_owner`;
CREATE TABLE `exception_owner` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `exception_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exception_owner_exception_id_foreign` (`exception_id`),
  KEY `exception_owner_user_id_foreign` (`user_id`),
  CONSTRAINT `exception_owner_exception_id_foreign` FOREIGN KEY (`exception_id`) REFERENCES `exception` (`id`) ON DELETE CASCADE,
  CONSTRAINT `exception_owner_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for exception_type
-- ----------------------------
DROP TABLE IF EXISTS `exception_type`;
CREATE TABLE `exception_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `active` bit(1) DEFAULT b'1',
  `remark` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_asn1wl0uc3x6elbn9wllg34et` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for model
-- ----------------------------
DROP TABLE IF EXISTS `model`;
CREATE TABLE `model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `brand` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `model` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `active` bit(1) DEFAULT b'1',
  `remark` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `model_model_unique` (`model`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `module` varchar(50) DEFAULT NULL,
  `module_id` varchar(50) DEFAULT NULL,
  `action` varchar(50) DEFAULT NULL,
  `remark` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `operation_editor_foreign` (`editor`),
  KEY `operation_creator_foreign` (`creator`),
  CONSTRAINT `operation_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `operation_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `brand` bigint DEFAULT NULL,
  `customer` bigint DEFAULT NULL,
  `identity` varchar(50) DEFAULT NULL,
  `contact` bigint DEFAULT NULL,
  `planner` bigint DEFAULT NULL,
  `producer` bigint DEFAULT NULL,
  `seller` bigint DEFAULT NULL,
  `shooting_month` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_identity_foreign` (`identity`) USING BTREE,
  KEY `order_creator_foreign` (`creator`),
  KEY `order_editor_foreign` (`editor`),
  KEY `order_customer_foreign` (`customer`),
  KEY `order_brand_foreign` (`brand`),
  KEY `order_seller_foreign` (`seller`),
  KEY `order_contact_foreign` (`contact`),
  KEY `order_planner_foreign` (`planner`),
  KEY `order_producer_foreign` (`producer`),
  CONSTRAINT `order_brand_foreign` FOREIGN KEY (`brand`) REFERENCES `brand` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_contact_foreign` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `order_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `order_customer_foreign` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `order_planner_foreign` FOREIGN KEY (`planner`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_producer_foreign` FOREIGN KEY (`producer`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_seller_foreign` FOREIGN KEY (`seller`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for package
-- ----------------------------
DROP TABLE IF EXISTS `package`;
CREATE TABLE `package` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `package_creator_foreign` (`creator`),
  KEY `package_editor_foreign` (`editor`),
  CONSTRAINT `package_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `package_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for package_service
-- ----------------------------
DROP TABLE IF EXISTS `package_service`;
CREATE TABLE `package_service` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `delivery` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `price` decimal(11,2) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `service` bigint DEFAULT NULL,
  `package` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `package_service_service_foreign` (`service`),
  KEY `package_service_package_foreign` (`package`),
  CONSTRAINT `package_service_package_foreign` FOREIGN KEY (`package`) REFERENCES `package` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `package_service_service_foreign` FOREIGN KEY (`service`) REFERENCES `service` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `collection` varchar(255) DEFAULT NULL,
  `action` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `permissions` json DEFAULT NULL,
  `validation` json DEFAULT NULL,
  `presets` json DEFAULT NULL,
  `fields` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `authority` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `permission_authority_foreign` (`authority`),
  CONSTRAINT `permission_authority_foreign` FOREIGN KEY (`authority`) REFERENCES `authority` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=631 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for quotation
-- ----------------------------
DROP TABLE IF EXISTS `quotation`;
CREATE TABLE `quotation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `identity` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `subtotal` decimal(19,2) DEFAULT NULL,
  `discount` decimal(8,2) DEFAULT NULL,
  `total_comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `discount_comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `subtotal_comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `explanations` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `customer` bigint NOT NULL,
  `brand` bigint DEFAULT NULL,
  `seller` bigint DEFAULT NULL,
  `package` bigint DEFAULT NULL,
  `pdf` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `quotation_identity_unique` (`identity`) USING BTREE,
  KEY `quotation_creator_foreign` (`creator`),
  KEY `quotation_editor_foreign` (`editor`),
  KEY `quotation_customer_foreign` (`customer`),
  KEY `quotation_brand_foreign` (`brand`),
  KEY `quotation_package_foreign` (`package`),
  KEY `quotation_seller_foreign` (`seller`),
  KEY `quotation_pdf_foreign` (`pdf`),
  CONSTRAINT `quotation_brand_foreign` FOREIGN KEY (`brand`) REFERENCES `brand` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `quotation_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `quotation_customer_foreign` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `quotation_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `quotation_package_foreign` FOREIGN KEY (`package`) REFERENCES `package` (`id`) ON DELETE SET NULL,
  CONSTRAINT `quotation_pdf_foreign` FOREIGN KEY (`pdf`) REFERENCES `attachment` (`id`) ON DELETE SET NULL,
  CONSTRAINT `quotation_seller_foreign` FOREIGN KEY (`seller`) REFERENCES `user` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for quotation_service
-- ----------------------------
DROP TABLE IF EXISTS `quotation_service`;
CREATE TABLE `quotation_service` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` int NOT NULL,
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` decimal(8,2) NOT NULL,
  `explanation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `service` bigint NOT NULL,
  `quotation` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `quotation_service_service_foreign` (`service`),
  KEY `quotation_service_quotation_foreign` (`quotation`),
  CONSTRAINT `quotation_service_quotation_foreign` FOREIGN KEY (`quotation`) REFERENCES `quotation` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `quotation_service_service_foreign` FOREIGN KEY (`service`) REFERENCES `service` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for receive
-- ----------------------------
DROP TABLE IF EXISTS `receive`;
CREATE TABLE `receive` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(11,2) NOT NULL,
  `receipt_date` date NOT NULL,
  `settlement_contract` bigint DEFAULT NULL,
  `order_contract` bigint DEFAULT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `receive_editor_foreign` (`editor`),
  KEY `receive_settlement_contract_foreign` (`settlement_contract`),
  KEY `receive_order_contract_foreign` (`order_contract`),
  KEY `receive_creator_foreign` (`creator`),
  CONSTRAINT `receive_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `receive_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `receive_order_contract_foreign` FOREIGN KEY (`order_contract`) REFERENCES `contract` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `receive_settlement_contract_foreign` FOREIGN KEY (`settlement_contract`) REFERENCES `settlement_contract` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `directus_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name_unique` (`name`),
  KEY `role_directus_role_foreign` (`directus_role`),
  KEY `role_creator_foreign` (`creator`),
  KEY `role_editor_foreign` (`editor`),
  CONSTRAINT `role_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `role_directus_role_foreign` FOREIGN KEY (`directus_role`) REFERENCES `directus_roles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `role_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for role_authority
-- ----------------------------
DROP TABLE IF EXISTS `role_authority`;
CREATE TABLE `role_authority` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `authority_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_authority_role_id_foreign` (`role_id`),
  KEY `role_authority_authority_id_foreign` (`authority_id`),
  CONSTRAINT `role_authority_authority_id_foreign` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`) ON DELETE SET NULL,
  CONSTRAINT `role_authority_role_id_foreign` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for service
-- ----------------------------
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `delivery` varchar(255) DEFAULT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cost` bigint NOT NULL,
  `level` int NOT NULL,
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `active` bit(1) DEFAULT b'1',
  `parent` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `service_name_unique` (`name`),
  KEY `service_cost_foreign` (`cost`),
  KEY `service_parent_foreign` (`parent`),
  CONSTRAINT `service_cost_foreign` FOREIGN KEY (`cost`) REFERENCES `cost` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `service_parent_foreign` FOREIGN KEY (`parent`) REFERENCES `service` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for settlement_contract
-- ----------------------------
DROP TABLE IF EXISTS `settlement_contract`;
CREATE TABLE `settlement_contract` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `identity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `receivable` decimal(11,2) NOT NULL,
  `contract_date` date NOT NULL,
  `remark` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `contract` bigint DEFAULT NULL,
  `order` bigint DEFAULT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `settlement_contract_identity_unique` (`identity`),
  KEY `settlement_contract_order_foreign` (`order`),
  KEY `settlement_contract_editor_foreign` (`editor`),
  KEY `settlement_contract_creator_foreign` (`creator`),
  KEY `settlement_contract_contract_foreign` (`contract`),
  CONSTRAINT `settlement_contract_contract_foreign` FOREIGN KEY (`contract`) REFERENCES `contract` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `settlement_contract_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `settlement_contract_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `settlement_contract_order_foreign` FOREIGN KEY (`order`) REFERENCES `order` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for storage
-- ----------------------------
DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `storage_name_unique` (`name`),
  KEY `storage_editor_foreign` (`editor`),
  KEY `storage_creator_foreign` (`creator`),
  CONSTRAINT `storage_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `storage_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `directus_user` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `directus_password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `employee_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `internal_position` varchar(255) DEFAULT NULL,
  `external_position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `lead_departments` varchar(255) DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `source` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role` bigint DEFAULT NULL,
  `auth` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `creator` bigint DEFAULT NULL,
  `editor` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `directus_email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_editor_foreign` (`editor`),
  KEY `user_creator_foreign` (`creator`),
  KEY `user_role_foreign` (`role`),
  KEY `user_directus_user_foreign` (`directus_user`),
  CONSTRAINT `user_creator_foreign` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `user_directus_user_foreign` FOREIGN KEY (`directus_user`) REFERENCES `directus_users` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `user_editor_foreign` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `user_role_foreign` FOREIGN KEY (`role`) REFERENCES `role` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_department
-- ----------------------------
DROP TABLE IF EXISTS `user_department`;
CREATE TABLE `user_department` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `department_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_department_department_id_foreign` (`department_id`),
  KEY `user_department_user_id_foreign` (`user_id`),
  CONSTRAINT `user_department_department_id_foreign` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_department_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
