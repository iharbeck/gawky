<html>
<head>
  <title>Welcome!</title>
</head>
<body>
  <h1>Welcome ${user}!</h1>
  <p>Our latest product:
  <a href="${latestProduct.url}">${latestProduct.name}</a>!
   abc צהü abc
 <#list ["winter", "spring", "summer", "autumn"] as x>
    --  ${x?cap_first}
 </#list> 
 <#setting number_format="0.##"> 
 <#assign test1 = "45123.55"?number * 10.5>
 <#assign test1 = "15523.59"?number>
 ${test1}
 ${test1?string("0.##")}
 ${test1?string("0.00")}
 ${test1?c}
 
 <#assign test2 = test1*100/100>
 ${test2}
</body>
</html>   
 