<?php
if (sizeof($_POST) > 0) 
{
	$genre=array ('Monsieur','Madame','Mademoiselle');
	if (in_array ($_POST['genre'],$genre))
	{
		echo "Choix s�lectionn� ".$_POST['genre'];
	}
	else
	{
		echo "Champ non trouv�";
	}
}
?>
<html>
<body>
<form name="saisie" method="POST" action="form7.php">
  Faites votre choix 
  <select name="genre">
    <option selected> </option>
    <option value="Monsieur">Monsieur</option>
    <option value="Madame">Madame</option>
    <option value="Mademoiselle">Mademoiselle</option>
  </select>
  <input name="Confirmer" type="submit" value="Confirmer">
</form>
</body>
</html>