# DG-Lab Unlocker
某APP的奇怪xposed模块

![](https://img.shields.io/badge/Module-Xposed-green?style=for-the-badge&logo=Android&logoColor=white) ![](https://img.shields.io/github/license/SakuraKoi/DgLabUnlocker?style=for-the-badge) ![](https://img.shields.io/github/languages/top/SakuraKoi/DgLabUnlocker?style=for-the-badge) ![](https://img.shields.io/github/downloads/SakuraKoi/DgLabUnlocker/total?style=for-the-badge) ![](https://img.shields.io/github/v/release/SakuraKoi/DgLabUnlocker?style=for-the-badge)

> 当前适配版本: `1.2.6` `1.3.1` `1.3.2`\
> 项目开发将以当前支持的最新版本为目标, 在老版本中不保证所有功能可用

### 功能

- **解锁远程控制最大强度到276**\
最高100完全不够用好吧\
`再也不用担心high到一半自己起来调强度了 (超小声`

- **暴力锁死基础强度**\
理论上拦截所有远程加基础强度的BUG, 有效避免突然惨遭弹射起飞\
`有坏蛋试了半小时翻倍bug, 咱不说是谁`

- **无视远程最大强度限制**\
字面意思, 想拉多高拉多高 (坏.jpg\
`不要拿去干坏事qwq`

- **强制确保远程强度不超过限制** \
  避免魔改客户端搞事情\
  `上面那个功能的防御`

- **开放强度调整接口**\
  对外开放[Websocket RPC服务](https://github.com/SakuraKoi/DgLabUnlocker/wiki/Websocket-RPC-Spec)端口, 便于程序员整点花活 #8\
  <strike>`此功能最终解释权归永仪所有`</strike>\
  `咱不对小可爱被玩坏承担任何责任`
