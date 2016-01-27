
<?php 


$dir    = 'uploads';
$files1 = scandir($dir);


foreach ($files1 as $value) {
    
	echo "<ul>";
	echo "<li>" . $value . "</li>";
	echo "</ul>";
	
	
}








?>
