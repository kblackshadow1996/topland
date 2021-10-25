1. 新增客户 POST /customer/add

```json
{
  "name": "阿里巴巴",
  "source": "ACTIVE",
  "type": "COMPANY",
  "seller": "1",
  "invoice_type": "NORMAL",
  "identity": "12384302842374980",
  "account": "ALIBABA COMPANY",
  "mobile": "13878942415",
  "bank": "建设银行",
  "post_address": "杭州",
  "register_address": "杭州",
  "business": "生产资本家",
  "contacts": [
    {
      "name": "小李",
      "address": "杭州",
      "position": "主管",
      "department": "销售部",
      "gender": "MALE",
      "mobile": "14579867611",
      "remark": "销售牛人"
    }
  ],
  "creator": 1
}
```

2. 更新客户 PATCH /customer/update/:id

```json
{
  "name": "阿里巴巴-update",
  "source": "ACTIVE",
  "type": "COMPANY",
  "seller": "1",
  "invoice_type": "NORMAL",
  "identity": "12384302842374980",
  "account": "ALIBABA COMPANY",
  "mobile": "13878942415",
  "bank": "建设银行",
  "post_address": "杭州",
  "register_address": "杭州",
  "business": "生产资本家",
  "contacts": [
    {
      "name": "小天天",
      "address": "杭州",
      "position": "主管",
      "department": "销售部",
      "gender": "MALE",
      "mobile": "14579867611",
      "remark": "销售牛人"
    },
    {
      "id": "9",
      "name": "小王-update",
      "address": "杭州",
      "position": "主管",
      "department": "销售部",
      "gender": "MALE",
      "mobile": "14579867612",
      "remark": "渣渣"
    }
  ],
  "creator": 1
}
```

3. 流失客户 PATCH /customer/lost/:id

```json
{
  "lost_reason": "该客户自身原因",
  "creator": 1
}
```

4. 寻回 PATCH /customer/retrieve/:id

```json
{
  "creator": 1
}
```

5. 新增报价合同 POST /quotation/add

```json
{
  "customer": 27,
  "seller": 1,
  "title": "百度科技-修改版5&图澜公司报价表",
  "subtotal": "10000.01",
  "discount": "100.01",
  "subtotalComment": "小计价格",
  "discountComment": "优惠价格",
  "totalComment": "总价格",
  "explanations": "第一条说明#第二条说明#第三条说明",
  "creator": 1,
  "services": [
    {
      "service": 1,
      "unit": "张",
      "price": "10000.01",
      "amount": "1",
      "explanation": "这是第一条报价服务"
    }
  ]
}
```

6. 更新报价合同 PATCH /quotation/update/:id

```json
{
  "customer": 27,
  "seller": 1,
  "title": "百度科技-修改版5&图澜公司报价表-修改版2",
  "subtotal": "10000.01",
  "discount": "1000.01",
  "subtotal_comment": "小计价格",
  "discount_comment": "优惠价格",
  "total_comment": "总价格",
  "explanations": "第一条说明#第二条说明#第三条说明",
  "creator": 1,
  "services": [
    {
      "service": 1,
      "unit": "张",
      "price": "10000.01",
      "amount": "1",
      "explanation": "这是第二条报价服务修改版2"
    }
  ]
}
```

7. 新增品牌 POST /brand/add

```json
{
  "name": "蚂蚁金服",
  "customer": 7,
  "seller": 1,
  "producer": 1,
  "business": "金融支付",
  "creator": 1,
  "contacts": [
    {
      "name": "大沙雕",
      "address": "杭州",
      "position": "主管",
      "department": "客服部",
      "gender": "MALE",
      "mobile": "14579867611",
      "remark": "纯沙口"
    }
  ]
}
```

8. 更新品牌 PATCH /brand/update/:id

```json
{
  "name": "蚂蚁金服科技",
  "customer": 7,
  "seller": 1,
  "producer": 1,
  "business": "金融支付",
  "creator": 1,
  "contacts": [
    {
      "id": "37",
      "name": "大沙雕",
      "address": "杭州",
      "position": "主管",
      "department": "客服部",
      "gender": "MALE",
      "mobile": "14579867611",
      "remark": "纯沙口"
    }
  ]
}
```

9. 新增合同 POST /contract/add

```json
{
  "type": "YEAR",
  "customer": 7,
  "brand": 1,
  "contract_date": "2021-11-11",
  "start_date": "2021-11-11",
  "end_date": "2022-11-11",
  "margin": "300000",
  "guarantee": "100000",
  "receivable": "1000000",
  "seller": 1,
  "remark": "第一份合同",
  "creator": 1,
  "action": "SUBMIT"
}
```

10. 受到纸质合同 PATCH /contract/receive-paper/:id

```json
{
  "paper_date": "2021-11-16",
  "attachments": [
    {
      "file": "8f8722b4-4647-4f4d-b39b-9c7ad1608f2b"
    }
  ],
  "creator": 1
}
```

11. 审核合同 PATCH /contract/review/:id

```json
{
  "action": "REJECT",
  "review_comment": "十分滴不合理",
  "creator": 1
}
```

```json
{
  "action": "APPROVE",
  "review_comment": "海星",
  "creator": 1
}
```

12. 新增产品套餐 POST /package/add

```json
{
  "name": "拍摄套餐",
  "remark": "第一个产品套餐",
  "services": [
    {
      "service": 1,
      "unit": "张",
      "price": "600",
      "delivery": "成片"
    }
  ],
  "creator": 1
}
```

13. 编辑产品套餐 PATCH /package/update/:id

```json
{
  "name": "拍摄套餐-1",
  "remark": "第一个产品套餐",
  "services": [
    {
      "id": 1,
      "service": 1,
      "unit": "张",
      "price": "600",
      "delivery": "成片"
    },
    {
      "service": 1,
      "unit": "张",
      "price": "600",
      "delivery": "半成品"
    }
  ],
  "creator": 1
}
```

14. 新增异常 POST /exception/add

```json
[
  {
    "attribute": "INTERNAL",
    "type": "1",
    "department_source": "INTERNAL",
    "department": 2,
    "owners": [
      1
    ],
    "copies": [
      1
    ],
    "judge": 1,
    "complaint": "丢失很多素材",
    "self_check": "都是小王的锅",
    "narrative": "小王表示不是很认同",
    "estimated_loss": "1000.01",
    "estimated_loss_condition": "可能会跑来要钱",
    "create_date": "2021-11-11",
    "creator": 1
  }
]
```

15. 更新异常 PATCH /exception/update/:id

```json
{
  "attribute": "INTERNAL",
  "type": "1",
  "department_source": "INTERNAL",
  "department": 2,
  "owners": [
    1
  ],
  "copies": [
    1
  ],
  "judge": 1,
  "complaint": "丢失很多素材",
  "self_check": "都是小王的锅",
  "narrative": "小王表示不是很认同",
  "estimated_loss": "1000.01",
  "estimated_loss_condition": "可能会跑来要钱",
  "create_date": "2021-11-11",
  "creator": 1
}
```

16. 解决异常 PATCH /exception/solve/:id

```json
{
  "close_date": "2021-12-12",
  "actual_loss": "100",
  "actual_loss_condition": "不亏",
  "solution": "处理方案",
  "optimal": "true",
  "optimal_solution": "最优解决方案",
  "creator": 1
}
```

17. 同步组织 POST /department/wework/sync/all

18. 同步单个组织 POST /department/wework/sync/:id1?creator=:id2

19. 同步用户 POST /user/wework/sync/all?creator=:id

20. 同步单个组织用户 POST /user/wework/sync?deptId=:id1&creator=:id2

21. 创建角色 POST /role/add

```json
{
  "name": "销售经理",
  "remark": "这是备注",
  "authorities": [
    1,
    2,
    3,
    4,
    5
  ],
  "creator": 1
}
```

22. 更新角色 PATCH /role/update/:id

```json
{
  "name": "销售经理-update ",
  "remark": "这是备注",
  "authorities": [
    1,
    2,
    3,
    4
  ],
  "creator": 1
}
```

23. 单个用户授权 PATCH /user/auth/:id

```json
{
  "role": 2,
  "auth": "ALL",
  "creator": 1
}
```

24. 批量授权 PATCH /user/auth

```json
{
  "users": [
    10,
    11,
    12
  ],
  "role": 2,
  "auth": "ALL",
  "creator": 1
}
```

25. 新增结算合同 POST /settlement-contract/add

```json
{
  "contract_date": "2021-10-18",
  "receivable": "10000",
  "remark": "写个备注",
  "attachments": [
    {
      "file": "2dc57e24-ca41-4682-ae56-3660d048a6e5"
    }
  ],
  "contract": 1,
  "action": "SUBMIT",
  "creator": 1
}
```

26. 审核结算合同 PATCH /settlement-contract/review/:id

```json
{
  "action": "REJECT",
  "creator": 1,
  "review_comments": "不合理"
}
```

```json
{
  "action": "APPROVE",
  "creator": 1,
  "review_comments": "合理"
}
```