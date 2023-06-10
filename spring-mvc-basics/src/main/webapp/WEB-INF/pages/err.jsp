<%@ include file="./header.jspf" %>

<h3 class="text-danger">OOPS! There was an error while processing your request</h3>

<p>Please retry after a while, and if the error persists, please write to 
<a href="mailto:helpdesk@kvinod.com">helpdesk@kvinod.com</a></p>


<button class="btn btn-link" onclick="showErrorDetails()">Show technical details</button>

<pre style="visibility: hidden" id="errDeatils"><%

Exception ex = (Exception) request.getAttribute("ex");
ex.printStackTrace(new java.io.PrintWriter(out));

%></pre>

<%@ include file="./footer.jspf" %>

<script>
function showErrorDetails() {
	document.getElementById("errDeatils").style.visibility="visible";
}
</script>