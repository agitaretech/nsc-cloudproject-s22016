 <?php $file = 'myfile.txt';

        $postdata = file_get_contents("php://input");
        $data = json_decode($postdata, true);


        $data_insert = "\nfileName--> " . $data['fileName'] .
				", Tag1--> " . $data['tag1'] .
                ", Tag2--> " . $data['tag2'] . 
                ", Tag3--> " . $data['tag3'];


        //print $data_insert;

        file_put_contents($file, $data_insert, FILE_APPEND | LOCK_EX);