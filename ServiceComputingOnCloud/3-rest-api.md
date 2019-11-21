---
title: REST API
---

## 模仿 Github API 编写设计博客网站的部分 REST API
### [返回目录](../ServiceComputingOnCloud-Catalog)

### 域名
```
https://api.blog.com
```
### 请求或返回数据格式
Json
### http 动词
名称|含义
:-:|:-:
GET|从服务器取出资源
POST|在服务器新建一个资源
PUT|在服务器更新资源（客户端提供改变后的完整资源）
DELETE|从服务器删除资源
### API 列表
#### 登陆账号/授权
```
curl -u username:password https://api.blog.com/users
```
#### 获得用户主页
- 表示
    ```
    GET /users/:username
    ```
- 例子
    ```
    curl -i -u -X GET https://api.blog.com/users/guojj
    ```
- 响应
    ```json
    Status 200 OK
    {
        "username": "guojj",
        "userid": 1,
        "url": "https://api.blog.com/users/guojj",
        "html_url": "https://blog.com/users/guojj",
        "location": "Guangzhou, China",
        "article_url": "https://api.blog.com/articles/guojj",
        "organization": "Sun Yat-sen University"
    }
    ```
#### 获得用户所有博客列表
- 表示  
    ```
    GET /articles/:username
    ```
- 例子  
    ```
    curl -i -u -X GET https://api.blog.com/articles/guojj
    ```
- 响应  
    ```json
    Status: 200 OK
    {
        "total_count": 1,
        "articles": [
            {
                "id": 1,
                "title": "XXX",
                "isOriginal": true,
                "owner": {
                    "username": "guojj",
                    "userid": 1,
                    "url": "https://api.blog.com/users/guojj",
                    "html_url": "https://blog.com/users/guojj"
                },
                "url": "https://api.blog.com/articles/guojj/1",
                "html_url": "https://blog.com/articles/guojj/1",
                "description": "XXX",
                "created_at": "2019-11-21T12:00:00Z",
                "updated_at": "2019-11-21T13:00:00Z",
                "read_count": 1
            }
        ]
    }
    ```
#### 获得一篇博客详细内容
- 表示  
    ```
    GET /articles/:username/{id}
    ```
- 例子  
    ```
    curl -i -u -X GET https://api.blog.com/articles/guojj/1
    ```
- 响应  
    ```json
    Status: 200 OK
    {
        "id": 1,
        "title": "XXX",
        "private": false,
        "isOriginal": true,
        "owner": {
            "username": "guojj",
            "userid": 1,
            "url": "https://api.blog.com/users/guojj",
            "html_url": "https://blog.com/users/guojj"
        },
        "url": "https://api.blog.com/articles/guojj/1",
        "html_url": "https://blog.com/articles/guojj/1",
        "description": "XXX",
        "created_at": "2019-11-21T12:00:00Z",
        "updated_at": "2019-11-21T13:00:00Z",
        "read_count": 1
    }
    ```
#### 更新一篇博客
- 表示  
    ```
    PUT /articles/:username/{id}
    ```
- 所需参数  
    名称 |类型 |可选性|含义
    :--:|:--:|:--:|:--:
    content|string|Required|修改后的博客内容
- 例子  
    ```
    curl -i -u -X PUT -d {"content":"xxx"} https://api.blog.com/articles/guojj/1
    ```
- 响应  
    ```json
    Status 201 CREATED
    {
        "id": 1,
        "title": "XXX",
        "private": false,
        "isOriginal": true,
        "owner": {
            "username": "guojj",
            "userid": 1,
            "url": "https://api.blog.com/users/guojj",
            "html_url": "https://blog.com/users/guojj"
        },
        "url": "https://api.blog.com/articles/guojj/1",
        "html_url": "https://blog.com/articles/guojj/1",
        "description": "XXX",
        "created_at": "2019-11-21T12:00:00Z",
        "updated_at": "2019-11-21T15:00:00Z",
        "read_count": 1
    }
    ```

#### 发布一篇博客
- 表示  
    ```
    POST /articles/:username
    ```
- 所需参数  
    名称 |类型 |可选性|含义
    :--:|:--:|:--:|:--:
    title|string|Required|标题
    description|string|Required|内容简介
    isOriginal|bool|Required|是否原创
    isPrivate|bool|Required|是否仅自己可见
    content|string|Required|博客内容
- 例子  
    ```
    curl -i -u -X POST -d {"title":"xxx", "description":"xxx", "isOriginal":true, "isPrivate":flase "content":"xxx"} https://api.blog.com/articles/guojj
    ```
- 响应  
    ```json
    Status 201 CREATED
    {
        "id": 2,
        "title": "XXX",
        "private": false,
        "isOriginal": true,
        "owner": {
            "username": "guojj",
            "userid": 1,
            "url": "https://api.blog.com/users/guojj",
            "html_url": "https://blog.com/users/guojj"
        },
        "url": "https://api.blog.com/articles/guojj/2",
        "html_url": "https://blog.com/articles/guojj/2",
        "description": "XXX",
        "created_at": "2019-11-21T17:00:00Z",
        "updated_at": "2019-11-21T17:00:00Z",
        "read_count": 1
    }
    ```

#### 删除一篇博客
- 表示
    ```
    DELETE /articles/:username/{id}
    ```
- 例子
    ```
    curl -i -u -X DELETE https://api.blog.com/articles/guojj/1
    ```
- 响应
    ```json
    Status 200 OK
    {
        "id":1,
        "owner": {
            "username": "guojj",
            "userid": 1,
            "url": "https://api.blog.com/users/guojj",
            "html_url": "https://blog.com/users/guojj"
        },
        "deleted_at": "2019-11-21T18:00:00Z"
    }
    ```
#### 按内容搜索博客
- 表示
    ```
    POST /search/articles{username}{content}
    ```
- 所需参数
    名称 |类型 |可选性|含义
    :--:|:--:|:--:|:--:
    username|string|Optional|只在该用户的博客中寻找
    content|string|Required|搜索内容
- 例子
    ```
    curl -i -u https://api.blog.com/search/articles?username=guojj&content=xxx
    ```
- 响应
    ```json
    Status 200 OK
    {
        "search_content": "xxx",
        "total_related_count": 1,
        "articles": [
            {
                "id": 1,
                "title": "XXX",
                "isOriginal": true,
                "owner": {
                    "username": "guojj",
                    "userid": 1,
                    "url": "https://api.blog.com/users/guojj",
                    "html_url": "https://blog.com/users/guojj"
                },
                "url": "https://api.blog.com/articles/guojj/1",
                "html_url": "https://blog.com/articles/guojj/1",
                "description": "XXX",
                "created_at": "2019-11-21T12:00:00Z",
                "updated_at": "2019-11-21T13:00:00Z",
                "read_count": 1
            }
        ]
    }
    ```
---

[返回目录](..//ServiceComputingOnCloud-Catalog)