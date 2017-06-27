<table width="645" border="0" cellpadding="0" cellspacing="0">
 <!-- Feature Item Images -->
 <tr>
 <!-- Spacer for seperation -->
 <td width="10"><img src="images/spacer.gif" border="0" height="1" width="10" alt=""></td>
 
<logic:iterate id="item" name="FeaturedItemsKey">  
 <td width="104" align="center">
  <html:link page="/action/viewitemdetail" paramId="id" paramName="item" paramProperty="id">
    <img border="0" src="<bean:write name="item" property="smallImageURL"/>"/>
  </html:link>
 </td> 
 </logic:iterate>
 
 </tr>
</table>

<img src="images/spacer.gif" border="0" height="10" width="" alt=""><br>
<table width="645" border="0" cellpadding="0" cellspacing="0" >
 <!-- PRODUCT DESCRIPTION ROW -->
 
 <tr>
 
 <logic:iterate id="item" name="FeaturedItemsKey"> 
 
 <td width="21"><img src="images/spacer.gif" border="0" height="1" width="21" alt=""></td>
 <td width="97" valign="top">
   <span><b>
   <html:link page="/action/viewitemdetail" paramId="id" paramName="item" paramProperty="id">
      <bean:write name="item" property="name"/>
  </html:link>
   
   </b><br><br></span>
   <b>Price: $<bean:write name="item" format="#,##0.00" property="unitPrice"/></b><br>
   <span class="feature-text"><bean:write name="item" property="description"/><br></span>			
 </td>
 <td width="1" bgcolor="#999999"><img src="images/spacer.gif" border="0" height="1" width="1" alt=""></td>
  </logic:iterate>
  
  <td width="6"><img src="images/spacer.gif" border="0" height="1" width="6" alt=""></td>
  
  
 </tr>
 
<tr>
 <td width="21"><img src="images/spacer.gif" border="0" height="1" width="21" alt=""></td>
 <td width="97" valign="top"><br>   
   <img src="images/spacer.gif" align="left" border="0" height="10" width="3" alt="">
   <span class="feature-text">  
   <br>
   <img src="images/spacer.gif" border="0" height="1" width="1" alt=""></span>
 </td>

 <td width="1" bgcolor="#999999"><img src="images/spacer.gif" border="0" height="1" width="1" alt=""></td>
 <td width="6"><img src="images/spacer.gif" border="0" height="1" width="6" alt=""></td>
 <td width="97" valign="top"><br>   
   <img src="images/spacer.gif" align="left" border="0" height="10" width="3" alt="">
   <span class="feature-text"><br>
   <img src="images/spacer.gif" border="0" height="1" width="1" alt=""></span>
 </td>

 <td width="1" bgcolor="#999999"><img src="images/spacer.gif" border="0" height="1" width="1" alt=""></td>
 <td width="6"><img src="images/spacer.gif" border="0" height="1" width="6" alt=""></td>
 <td width="97" valign="top"><br>   
   <img src="images/spacer.gif" align="left" border="0" height="10" width="3" alt="">
   <span class="feature-text"><br>
   <img src="images/spacer.gif" border="0" height="1" width="1" alt=""></span>
 </td>

 <td width="1" bgcolor="#999999"><img src="images/spacer.gif" border="0" height="1" width="1" alt=""></td>
 <td width="6"><img src="images/spacer.gif" border="0" height="1" width="6" alt=""></td>
 <td width="97" valign="top"><br>   
   <img src="images/spacer.gif" align="left" border="0" height="10" width="3" alt="">
   <span class="feature-text"><br>
   <img src="images/spacer.gif" border="0" height="1" width="1" alt=""></span>
 </td>

 <td width="1" bgcolor="#999999"><img src="images/spacer.gif" border="0" height="1" width="1" alt=""></td>
 <td width="6"><img src="images/spacer.gif" border="0" height="1" width="6" alt=""></td>
 <td width="97" valign="top"><br>   
   <img src="images/spacer.gif" align="left" border="0" height="10" width="3" alt="">
   <span class="feature-text"><br>
   <img src="images/spacer.gif" border="0" height="1" width="1" alt=""></span>
 </td>

 <td width="1" bgcolor="#999999"><img src="images/spacer.gif" border="0" height="1" width="1" alt=""></td>
 <td width="6"><img src="images/spacer.gif" border="0" height="1" width="6" alt=""></td>
 <td width="97" valign="top"><br>   
   <img src="images/spacer.gif" align="left" border="0" height="10" width="3" alt="">
   <span class="feature-text"><br>
   <img src="images/spacer.gif" border="0" height="1" width="1" alt=""></span>
 </td>
</tr>
</table>
</td>