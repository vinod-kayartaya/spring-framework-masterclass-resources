<%@ include file="./header.jspf" %>

<h2>Enter the price range: </h2>

<div class="row">
	<div class="col">
		<form action="products-by-price-range" method="GET">
			
			<div class="row form-group">
				<label class="col-md-4" for="min">Minimum price</label>
				<div class="col-md-8">
					<input type="text" id="min" name="min" class="form-control" >
				</div>
			</div>
			
			<div class="row form-group">
				<label class="col-md-4" for="max">Maximum price</label>
				<div class="col-md-8">
					<input type="text" id="max" name="max" class="form-control" >
				</div>
			</div>
			
			<button class="btn btn-primary">Submit</button>
		</form>
	</div>
	
	<div class="col"></div>
</div>

<%@ include file="./footer.jspf" %>