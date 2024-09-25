-- 创建库
create database if not exists blog_system charset utf8;

use blog_system;

-- 创建blog表
drop table if exists blog;
create table blog(
    blogId int primary key auto_increment,
    title varchar(256),
    content text,
    postTime datetime,
    -- userId 就是文章作者的用户id
    userId int
);CHARACTER SET utf8mb4;

ALTER TABLE blog CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建user表
drop table if exists user;
create table user(
    userId int primary key auto_increment,
    userName varchar(50) unique,
    password varchar(50)
);

insert into blog values(null,"【网络协议详解】——IPv4（学习笔记）","# 🕒 1. IPv4地址概述
IPv4地址就是因特网上的**每一台主机（或路由器）的每一个接口**分配一个在全世界范围内是**唯一的32比特的标识符**。

IP地址由因特网名字和数字分配机构ICANN（Internet Corporation for Assigned Names and Numbers）进行分配。
- 我国用户可向亚太网络信息中心APNIC（Asia Pacific Network Information Center）申请IP地址，需要缴费。
- 2011年2月3日，互联网号码分配管理局IANA （由ICANN行使职能）宣布，IPv4地址已经分配完毕。
- 我国在2014至2015年也逐步停止了向新用户和应用分配IPv4地址。同时全面开展商用部署IPv6。","2023-06-23 15:03:34",3);
insert into blog values(null,'【技能拾遗】——Markdown+Typora/VSCode与LaTeX的使用','📖 前言：**Markdown** 是一种轻量型标记语言，是一种语法. 以 `.md` 结尾的文本文件就是 Markdown 文件。 相较于 **Word**，它更加像是 **HTML** 语言或是 $\LaTeX$，并不是最淳朴的那种"所见即所得"。 它处处透露着一种极简主义。 高效简洁清晰的同时，又很简单。 看起来舒服，语法简单，尤其在处理纯文本上有很大的优势。

它相较于 **Word**，兼容性非常高，可以跨平台使用，不用担心奇奇怪怪的版本兼容问题。它可以让你不再纠结什么字体，什么样式，什么排版。而且逻辑清晰，层次分明。同时，有许多网站都支持或正在使用 **Markdown** 语法。 如 **Github** （等一系列代码托管平台），StackOverflow（等答疑平台），简书、语雀、CSDN （等一系列笔记平台）。

![MarkDown图标](https://th.bing.com/th/id/OIP.gQ8h0y-sTEOuGVQKxds-wwAAAA?rs=1&pid=ImgDetMain)

___

',"2024-04-16 11:22:44",2);
insert into blog values(null,"个人介绍","大家好，我是HinsCoder，是一名大四学生，",now(),2);


insert into user values(null,"admin","123456");
insert into user values(null,"HinsCoder","111111");
insert into user values(null,"张三","123456");



