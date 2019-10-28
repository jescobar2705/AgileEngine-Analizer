# How To use

Provide all the necessary parameters.

java -cp Analizer.jar **_<input_origin_file_path> <input_other_sample_file_path> <target_element_id>_**

**Where:** 

input origin file: Is the base html file

Input Other Sample file : is the html file to compare

Target Element: Is the element to find and check.

#Sample Output

**Element Base PATH**:html>body>div>div>div>div>div>div>\<a id="make-everything-ok-button" class="btn btn-success" href="#ok" title="Make-Button" rel="next" onclick="javascript:window.okDone(); return false;"> Make everything OK \</a>

**Similar Element found PATH**:html>body>div>div>div>div>div>div>\<a class="btn btn-success" href="#check-and-ok" title="Make-Button" rel="done" onclick="javascript:window.okDone(); return false;"> Make everything OK \</a>