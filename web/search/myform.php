 <?php $file = 'myfile.txt';

        $postdata = file_get_contents("php://input");
        $data = json_decode($postdata, true);


        $data_insert = "\nfileName--> " . $data['alltags'];


        //print $data_insert;

        file_put_contents($file, $data_insert, FILE_APPEND | LOCK_EX);