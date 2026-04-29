# Security Documentation

## Roles

| Role | Permissions |
|------|------------|
| USER | Read-only: can view departments and employee list |
| ADMIN | Full access: can view all pages AND edit employees |

## What Is Protected

| Resource | USER | ADMIN |
|----------|------|-------|
| `/departments.xhtml` | ✅ Allowed | ✅ Allowed |
| `/employees.xhtml` | ✅ Allowed | ✅ Allowed |
| `/editEmployee.xhtml` | ❌ 403 Denied | ✅ Allowed |

## How Security Is Enforced

Security is enforced declaratively via `web.xml` using Payara container-managed security with the **file realm**.

```xml
<!-- ADMIN only -->
<security-constraint>
    <web-resource-collection>
        <url-pattern>/editEmployee.xhtml</url-pattern>
        <http-method>GET</http-method>
        <http-method>POST</http-method>
    </web-resource-collection>
    <auth-constraint>
        <role-name>ADMIN</role-name>
    </auth-constraint>
</security-constraint>

<!-- USER + ADMIN -->
<security-constraint>
    <web-resource-collection>
        <url-pattern>/departments.xhtml</url-pattern>
        <url-pattern>/employees.xhtml</url-pattern>
    </web-resource-collection>
    <auth-constraint>
        <role-name>USER</role-name>
        <role-name>ADMIN</role-name>
    </auth-constraint>
</security-constraint>
```

Authentication method: **BASIC** (browser login dialog)

## How to Test

### Test 1 — USER can view lists
1. Open incognito browser
2. Go to `http://localhost:8080/FinalProject/departments.xhtml`
3. Log in as `user1` / `password`
4. ✅ Should see department list

### Test 2 — USER denied from edit
1. While logged in as `user1`
2. Go to `http://localhost:8080/FinalProject/editEmployee.xhtml?empId=1`
3. ✅ Should see **HTTP 403 - Access Denied**

### Test 3 — ADMIN can edit
1. Open incognito browser
2. Go to `http://localhost:8080/FinalProject/employees.xhtml`
3. Log in as `admin1` / `password`
4. Click **Edit** on any employee
5. Change title or salary → click **Save Changes**
6. ✅ Should see "Employee updated successfully!"