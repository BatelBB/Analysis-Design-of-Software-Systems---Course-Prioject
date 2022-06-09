

# Appendix - example seed data

In the example seed, the store has three suppliers: "Office Stuff" providing office stuff, "Good Foods" providing organic food, and "Mystery Items" which provides mysterious possibly-cursed items (whose owner asked us to note here that he is, in fact, *not* the devil).

## Suppliers

| PPN               | 1                 |
| ----------------- | ----------------- |
| Name              | Office Stuff      |
| Contact name      | Ofir Office       |
| Contact phone     | 555-1234          |
| Contact email     | ofir@office.stuff |
| Bank #            | 1111              |
| Delivering        | true              |
| Payment condition | DirectDebit       |
| Supplies on       | SATURDAY          |




| PPN               | 2             |
| ----------------- | ------------- |
| Name              | Good Foods    |
| Contact name      | Tim Apple     |
| Contact phone     | 555-0001      |
| Contact email     | tim@good.food |
| Bank #            | 22222         |
| Delivering        | true          |
| Payment condition | DirectDebit   |
| Supplies on       | N/A           |




| PPN               | 666                 |
| ----------------- | ------------------- |
| Name              | Mystery Items       |
| Contact name      | Not Satan           |
| Contact phone     | 555-6666            |
| Contact email     | not_satan@hell.doom |
| Bank #            | 666666              |
| Delivering        | false               |
| Payment condition | Credit              |
| Supplies on       | N/A                 |



## Items

### From supplier: Office Stuff (PPN 1)


| Name           | Pen          |
| -------------- | ------------ |
| Price          | 10.00        |
| Category       | Writing      |
| Supplier PPN   | 1            |
| Supplier name  | Office Stuff |
| Catalog number | 1            |


| Name           | Notebook     |
| -------------- | ------------ |
| Price          | 5.00         |
| Category       | Paper        |
| Supplier PPN   | 1            |
| Supplier name  | Office Stuff |
| Catalog number | 2            |




### From supplier: Good Foods


| Name           | Whole Bread |
| -------------- | ----------- |
| Price          | 7.50        |
| Category       | Bread       |
| Supplier PPN   | 2           |
| Supplier name  | Good Foods  |
| Catalog number | 1           |


| Name           | Soy Milk           |
| -------------- | ------------------ |
| Price          | 6.00               |
| Category       | Dairy alternatives |
| Supplier PPN   | 2                  |
| Supplier name  | Good Foods         |
| Catalog number | 2                  |


### From supplier: Mystery Items


| Name           | Curse in a box |
| -------------- | -------------- |
| Price          | 1000.00        |
| Category       | Gifts for foes |
| Supplier PPN   | 666            |
| Supplier name  | Mystery Items  |
| Catalog number | 5              |


| Name           | Voodoo Doll     |
| -------------- | --------------- |
| Price          | 600.00          |
| Category       | Human Suffering |
| Supplier PPN   | 666             |
| Supplier name  | Mystery Items   |
| Catalog number | 2               |


| Name           | Monkey's Paw        |
| -------------- | ------------------- |
| Price          | 0.01                |
| Category       | No strings attached |
| Supplier PPN   | 666                 |
| Supplier name  | Mystery Items       |
| Catalog number | 1                   |

## Orders

### From supplier: Office Stuff (PPN 1)


| Supplier name: | Office Stuff | Supplier PPN: | 1           |
| -------------- | ------------ | ------------- | ----------- |
| Ordered:       | 2022/01/01   | Provided:     | 2022/01/05  |
| ----           | ----         | ----          | ----        |
| Pen            | 1            | 200 units     | $10.00 / ea |
| Notebook       | 2            | 200 units     | $5.00 / ea  |
| ----           | ----         | ----          | ----        |
| Total price:   | $2200.00     |               |             |




| Supplier name: | Office Stuff | Supplier PPN: | 1           |
| -------------- | ------------ | ------------- | ----------- |
| Ordered:       | 2022/03/01   | Provided:     | 2022/03/05  |
| ----           | ----         | ----          | ----        |
| Pen            | 1            | 95 units      | $10.00 / ea |
| Notebook       | 2            | 1000 units    | $5.00 / ea  |
| ----           | ----         | ----          | ----        |
| Total price:   | $4950.00     |               |             |


### From supplier: Good Foods (PPN 2)


| Supplier name: | Good Foods | Supplier PPN: | 2          |
| -------------- | ---------- | ------------- | ---------- |
| Ordered:       | 2022/01/05 | Provided:     | 2022/01/09 |
| ----           | ----       | ----          | ----       |
| Whole Bread    | 1          | 200 units     | $7.50 / ea |
| Soy Milk       | 2          | 200 units     | $6.00 / ea |
| ----           | ----       | ----          | ----       |
| Total price:   | $1710.00   |               |            |


| Supplier name: | Good Foods | Supplier PPN: | 2          |
| -------------- | ---------- | ------------- | ---------- |
| Ordered:       | 2022/03/02 | Provided:     | 2022/03/10 |
| ----           | ----       | ----          | ----       |
| Whole Bread    | 1          | 95 units      | $7.50 / ea |
| Soy Milk       | 2          | 1000 units    | $6.00 / ea |
| ----           | ----       | ----          | ----       |
| Total price:   | $5370.00   |               |            |


### From supplier: Mystery Items (PPN 3)


| Supplier name: | Mystery Items | Supplier PPN: | 666          |
| -------------- | ------------- | ------------- | ------------ |
| Ordered:       | 2021/05/06    | Provided:     | 2022/05/06   |
| ----           | ----          | ----          | ----         |
| Voodoo Doll    | 2             | 200 units     | $600.00 / ea |
| Monkey's Paw   | 1             | 200 units     | $0.01 / ea   |
| ----           | ----          | ----          | ----         |
| Total price:   | $96001.60     |               |              |


| Supplier name: | Mystery Items | Supplier PPN: | 666           |
| -------------- | ------------- | ------------- | ------------- |
| Ordered:       | 2022/05/06    | Provided:     | 2023/05/06    |
| ----           | ----          | ----          | ----          |
| Monkey's Paw   | 1             | 50 units      | $0.01 / ea    |
| Curse in a box | 5             | 1 units       | $1000.00 / ea |
| ----           | ----          | ----          | ----          |
| Total price:   | $1000.40      |               |               |


## Quality discounts

### From supplier: Office Stuff (PPN 1)


| Item name           | Pen          |
| ------------------- | ------------ |
| Item catalog number | 1            |
| For amounts over    | 100          |
| Discount            | 20.00%       |
| Supplier (name)     | Office Stuff |
| Supplier (ppn)      | 1            |




| Item name           | Pen          |
| ------------------- | ------------ |
| Item catalog number | 1            |
| For amounts over    | 200          |
| Discount            | 30.00%       |
| Supplier (name)     | Office Stuff |
| Supplier (ppn)      | 1            |




| Item name           | Pen          |
| ------------------- | ------------ |
| Item catalog number | 1            |
| For amounts over    | 500          |
| Discount            | 50.00%       |
| Supplier (name)     | Office Stuff |
| Supplier (ppn)      | 1            |




| Item name           | Notebook     |
| ------------------- | ------------ |
| Item catalog number | 2            |
| For amounts over    | 100          |
| Discount            | 20.00%       |
| Supplier (name)     | Office Stuff |
| Supplier (ppn)      | 1            |




### From supplier: Good Foods (PPN 2)

| Item name           | Whole Bread |
| ------------------- | ----------- |
| Item catalog number | 1           |
| For amounts over    | 100         |
| Discount            | 30.00%      |
| Supplier (name)     | Good Foods  |
| Supplier (ppn)      | 2           |

| Item name           | Whole Bread |
| ------------------- | ----------- |
| Item catalog number | 1           |
| For amounts over    | 150         |
| Discount            | 50.00%      |
| Supplier (name)     | Good Foods  |
| Supplier (ppn)      | 2           |

| Item name           | Whole Bread |
| ------------------- | ----------- |
| Item catalog number | 1           |
| For amounts over    | 50          |
| Discount            | 20.00%      |
| Supplier (name)     | Good Foods  |
| Supplier (ppn)      | 2           |

| Item name           | Soy Milk   |
| ------------------- | ---------- |
| Item catalog number | 2          |
| For amounts over    | 100        |
| Discount            | 20.00%     |
| Supplier (name)     | Good Foods |
| Supplier (ppn)      | 2          |


### From supplier: Mystery Items (PPN 3)


| Item name           | Voodoo Doll   |
| ------------------- | ------------- |
| Item catalog number | 2             |
| For amounts over    | 100           |
| Discount            | 20.00%        |
| Supplier (name)     | Mystery Items |
| Supplier (ppn)      | 666           |


| Item name           | Monkey's Paw  |
| ------------------- | ------------- |
| Item catalog number | 1             |
| For amounts over    | 50            |
| Discount            | 20.00%        |
| Supplier (name)     | Mystery Items |
| Supplier (ppn)      | 666           |


