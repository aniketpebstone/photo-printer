<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
 

<head>
<script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
<script type="text/javascript">
function getPhotos()
{
	$.get("/photos", function(photos) {
		console.log(photos)
	    $("#photos").html(photos);
	});
}

</script>
</head>
<body>
<pre>
Welcome ${userInfo.user.userName} (${userInfo.user.authorities[0].authority})  
<p>To get the photos from your google server please <button onClick='getPhotos()'> click here</button></p>
<p id='photos'></p>
</pre>
</body>