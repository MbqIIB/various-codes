<?php
if(!empty($_FILES))
{
    echo '<pre>';
    print_r($_FILES);
    echo '</pre>';
}
?>

<form method="POST" action="exemple5.php" enctype="multipart/form-data">
    Pi�ce jointe : <input type="file" name="file" /><br /><br />
    <input type="submit" value="Envoyer" />
</form>