<html>
<head>
  <title>Welcome!</title>
</head>
<body>
  <h1>Welcome ${user}!</h1>
  <p>Our latest product:
  <a href="${latestProduct.url}">${latestProduct.name}</a>!
   
 <#list ["winter", "spring", "summer", "autumn"] as x>
    --  ${x?cap_first}
 </#list> 

</body>
</html>   
 