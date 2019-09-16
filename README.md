# smart-xml-analyzer-task

Finds target element in input HTML and compares it with diff HTML.

## Usage

Assemble an executable jar:
```
sbt assembly
```

Run executable jar:
```
java -jar target/scala-2.12/smart-xml-analyzer-task.jar <input_origin_file_path> <input_other_sample_file_path> <element_id_to_find>
```

Example:
```
java -jar target/scala-2.12/smart-xml-analyzer-task.jar "./src/test/resources/samples/sample-0-origin.html" "./src/test/resources/samples/sample-4-the-mash.html" "make-everything-ok-button"
```

Or Run pre-build executable jar file
```
java -jar smart-xml-analyzer-task.jar <input_origin_file_path> <input_other_sample_file_path> <element_id_to_find>
```


Example:
```
java -jar smart-xml-analyzer-task.jar "./src/test/resources/samples/sample-0-origin.html" "./src/test/resources/samples/sample-4-the-mash.html" "make-everything-ok-button"
```