<?php $menu="compte";
require_once "include/config.inc.php";
?>

<?php
if (sizeof($_POST) > 0) 
{
$action = verif_GetPost($_POST['action']);

switch($action)
{
        case "Confirmer":
      
$oldpassword=mysqli_real_escape_string($connex,$_POST['oldpassword']);
$newpassword=mysqli_real_escape_string($connex,$_POST['newpassword']);
$newpassword2=mysqli_real_escape_string($connex,$_POST['newpassword2']);

$msg="";
if (empty($oldpassword)) $msg .= "Vous n'avez pas entr� votre ancien mot de passe.<br>";
if (empty($newpassword))    $msg .=  "Vous n'avez pas entr� de nouveau mot de passe.<br>";
if (empty($newpassword2))    $msg .= "Vous n'avez pas confirm� le nouveau mot de passe.<br>";
if ($newpassword!=$newpassword2)    $msg .= "Vos nouveaux mot de passe sont diff�rents.<br>";

if ($msg == "")
{
$sql="select * from user where idclef='".$_SESSION['idclef']."' ";
assert ('mysqli_query($connex, $sql)');
$qid = mysqli_query($connex, $sql);  
if (!$qid)  die ("Probleme :  " . mysqli_error($connex));
$row=mysqli_fetch_object( $qid);
$memoPass=$row->password;


 if ($memoPass != md5($oldpassword))
 {
 	$msg="Votre ancien mot de passe n'est pas valide.<br>";
 }
 else
 {
	$newpassword=md5($newpassword);
	$newpassword=mysqli_real_escape_string($connex,$newpassword);
  $sql="update user set password='$newpassword' where idclef='".$_SESSION['idclef']."'";
  assert ('mysqli_query($connex, $sql)');
  $qid=mysqli_query($connex, $sql);
  if (!$qid)  die ("Probleme :  " . mysqli_error($connex));
  else
  {
	  	require_once "head.inc.php";
	  echo "Votre nouveau mot de passe a �t� pris en compte.";
	  include("footer.inc.php");
	mysqli_free_result($qid);
	mysqli_close($connex);

  	  exit;
  }
 }
}

        break;


     default:
     break;
}

}
?>
<html>
<head></head>
<body>
<?php require_once "head.inc.php"; ?>
<span class="title">Changer de mot de passe</span> <br><br>

<p><?php if (isset($msg)) echo $msg; ?></p>
<form name="fpassword" method="post" action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>" >
  <table width=500 border="0" align="center" cellpadding="5" cellspacing="5">
    <tr>
        
      <td class=services_form>Ancien mot de passe:</td>
        <td><input class="texte" type="password" name="oldpassword" size=25>

        </td>
</tr>
<tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
</tr>
<tr>
        
      <td class=services_form>Nouveau mot de passe:</td>
        <td><input class="texte" type="password" name="newpassword" size=25>

        </td>
</tr>
<tr>
        
      <td class=services_form>Confirmation:</td>
        <td><input class="texte" type="password" name="newpassword2" size=25>

        </td>
</tr>
<tr>
        <td></td>
        <td>
        <INPUT name="action" TYPE="SUBMIT" class="texte" value="Confirmer">
           </td>
</table>
</form>

                

<?php include("footer.inc.php"); ?>
</body></html>