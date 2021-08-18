# SDK 对外开放文档站点
本站点依托于 GitHub Pages 静态站点托管机制，通过把 docs 下的 Markdown 文档转换成静态页面进而进行展示。


## docsify 介绍
当前所有的 Markdown 内容都在 docs 目录下面，通过使用 `docsify` 工具进行转化和目录维护。`docsify` 是一个动态生产文档网站的工具，不用于 GitBook、Hexo，它不会将 .md 转成 .html 文件，所有转化工作都是在运行时进行。

docsify 官方使用手册：[Docsify 快速开始](https://docsify.js.org/#/zh-cn/quickstart) 

## 本地运行站点
本地编写修改文档后，需要查看修改后的展示效果，可以在本地直接启动一个静态服务器来预览文件。

**Docsify 启动方式** <br>
本地要先安装 NodeJs，然后使用 NodeJs 的包管理工具 npm 来安装 Docsify：
```
npm i docsify-cli -g
```
进入项目目录，直接运行一下命令：
```
docsify serve docs
```
这样会在本地的 3000 端口启动一个静态网站来从 docs 中映射内容。

**Python 启动方式**<br>
如果你的系统里安装了 Python 的话，也可以很容易地启动一个静态服务器去预览你的网站。
```
> cd docs
> python -m http.server 3000
```
> 注意：这里的 Python 指 Python3，Python2 及早期版本命令有所不同

## 目录维护
站点左侧目录是通过 `docs/_sidebar.md` 中维护的，若目录需要修改，直接修改此文件即可，跳转链接是相对链接，注意链接文件的名称。