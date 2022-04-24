# DG-Lab Unlocker
某APP的奇怪xposed模块

![](https://img.shields.io/github/license/SakuraKoi/DgLabUnlocker?style=for-the-badge) ![](https://img.shields.io/github/languages/top/SakuraKoi/DgLabUnlocker?style=for-the-badge) ![](https://img.shields.io/github/downloads/SakuraKoi/DgLabUnlocker/total?style=for-the-badge) ![](https://img.shields.io/github/v/release/SakuraKoi/DgLabUnlocker?style=for-the-badge)

> 当前适配版本: `1.2.6` `1.3.1`

### 功能

- **解锁远程控制最大强度到276**\
最高100完全不够用好吧\
`再也不用担心high到一半自己起来调强度了 (超小声`

- **暴力锁死基础强度**\
理论上拦截所有远程加基础强度的BUG, 有效避免突然惨遭弹射起飞\
`有坏蛋试了半小时翻倍bug, 咱不说是谁`

- **屏蔽超过296的非法强度** \
避免恶意用户发送超高强度直接烧掉你的设备的变压模块\
`这破协议居然没有上限 (大声BB`

- **无视远程最大强度限制**\
字面意思, 想拉多高拉多高 (坏.jpg\
`不要拿去干坏事qwq`

- **强制确保远程强度不超过限制** \
  避免魔改客户端搞事情\
  `上面那个功能的防御`

- **开放强度调整接口**\

  对外开放Websocket RPC服务端口, 便于程序员整点花活 #8\

  <strike>`此功能最终解释权归永仪所有`</strike>\

  `咱不对小可爱被玩坏承担任何责任`



### RPC接口

需要先在设置中打开RPC开关, 服务开放在 `ws://0.0.0.0:23301` 上

`id` 字段用于区分请求-响应, 请求的id将在响应中被原样回传, 建议使用随机值或顺序递增

1. **查询当前强度**

   请求

   ````
   {
     "id": 114514,
     "method": "queryStrength"
   }
   ````

   响应

   ```
   {
     "id": 114514,
     "code": 0, // 错误码
     "result": "ok",
     "data": {
       "baseStrengthA": 10, // 基础强度
       "baseStrengthB": 12,
       // 以下数据仅在远程时存在, 否则值为null
       "remoteStrengthA": 30, // 远程强度
       "remoteStrengthB": 19,
       "maxStrengthA": 60, // 被控方设置的强度限制
       "maxStrengthB": 100
     }
   }
   ```

2. **设置强度**

   `超过强度限制的请求将被忽略`

   请求

   ```
   {
     "id": 114514,
     "method": "setStrength",
     "data": {
       // 此处的强度值应为基础强度(即总强度-远程强度)
       "strengthA": 10,
       "strengthB": 10,
     }
   }
   ```

   响应

   ```
   {
     "id": 114514,
     "code": 0, // 错误码
     "result": "ok",
     "data": null
   }
   ```

3. **加减强度**

   `超过强度限制的请求将被忽略`

   请求

   ```
   {
     "id": 114514,
     "method": "addStrength",
     "data": {
       // true=A false=B
       "channel": true, 
       // 正加负减
       "strength": 10,
     }
   }
   ```

   响应

   ```
   {
     "id": 114514,
     "code": 0, // 错误码
     "result": "ok",
     "data": null
   }
   ```

