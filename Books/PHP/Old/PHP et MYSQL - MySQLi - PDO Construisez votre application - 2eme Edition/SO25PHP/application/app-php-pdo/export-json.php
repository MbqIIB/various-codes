<?php $menu="export";
require_once "include/config.inc.php";
?>
<?php require_once "head.inc.php"; ?>

<?php
$sql="select carnet.*,carnet.id as idlinks,user.id,user.idclef 
	 FROM carnet,user 
	 WHERE user.id=carnet.iduser AND carnet.iduser='".$_SESSION['iduser']."' AND user.idclef='".$_SESSION['idclef']."' ";


$qid=$cnx->prepare($sql);
$qid->execute();
$nbre = $qid->fetchColumn();
if (count($nbre)!=0)
{
$_json = array();
while( $obj=$qid->fetch(PDO::FETCH_OBJ) ) 
{
	$_json[] = $obj;
}		 	

$json= json_encode($_json);

$qid->closeCursor();
$cnx = null;


$resultat=json_decode($json, true);
?>
<table border="1" width="100%">
<tr>
<td>Nom</td>
<td>Prenom</td>
<td>Adresse1</td>
<td>Adresse2</td>
<td>Code Postal</td>
<td>Ville</td>
</tr>
<?php
foreach ($resultat as $cle=>$row) 
{
echo "<tr>";
echo "<td>".$row['nom']."</td>";    
echo "<td>".$row['prenom']."</td>";
echo "<td>".$row['adresse1']."</td>";
echo "<td>".$row['adresse2']."</td>";
echo "<td>".$row['codepostal']."</td>";
echo "<td>".$row['ville']."</td>";
echo "</tr>";
}

?>
</table>

<?php
} else {
	echo "Aucun enregistrement possible";
}



?>

<?php require_once ("footer.inc.php"); ?>
