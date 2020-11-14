$(function () {



	$("input[type='password'][data-eye]").each(function (i) {
		var $this = $(this),
			id = 'eye-password-' + i,
			el = $('#' + id);

		$this.wrap($("<div/>", {
			style: 'position:relative',
			id: id
		}));

		$this.css({
			paddingRight: 60
		});
		$this.after($("<div/>", {
			html: 'Show',
			class: 'btn btn-dark btn-sm',
			id: 'passeye-toggle-' + i,
		}).css({
			position: 'absolute',
			right: 10,
			top: ($this.outerHeight() / 2) - 12,
			padding: '2px 7px',
			fontSize: 12,
			cursor: 'pointer',
		}));

		$this.after($("<input/>", {
			type: 'hidden',
			id: 'passeye-' + i
		}));

		var invalid_feedback = $this.parent().parent().find('.invalid-feedback');

		if (invalid_feedback.length) {
			$this.after(invalid_feedback.clone());
		}

		$this.on("keyup paste", function () {
			$("#passeye-" + i).val($(this).val());
		});
		$("#passeye-toggle-" + i).on("click", function () {
			if ($this.hasClass("show")) {
				$this.attr('type', 'password');
				$this.removeClass("show");
				$(this).removeClass("btn-outline-light");
			} else {
				$this.attr('type', 'text');
				$this.val($("#passeye-" + i).val());
				$this.addClass("show");
				$(this).addClass("btn-outline-light");
			}
		});
	});

	$(".my-login-validation").submit(function () {
		var form = $(this);
		if (form[0].checkValidity() === false) {
			event.preventDefault();
			event.stopPropagation();
		}
		form.addClass('was-validated');
	});
	$(".ward-0-patient").hide();
	$(".ward-2-patient").hide();
	$(".ward-4-patient").hide();
	$("li.ward").click(function () {
		// console.log($(this).index());		
		// console.log("cholse");
		$(".ward-" + $(this).index() + "-patient").toggle("slow", function () {
			// Animation complete.

		});
	});

	google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);
  
        function drawChart() {
  
          var data = google.visualization.arrayToDataTable([
            ['Ward Number', 'Critical Patients Number'],
            ['Ward-1',  1],
            ['Ward-1',  3],
            ['Ward-1',  2]
          ]);
  
          var options = {
            title: 'Critical Patients'
          };
  
          var chart = new google.visualization.PieChart(document.getElementById('piechart'));
  
          chart.draw(data, options);
		}
		google.charts.setOnLoadCallback(drawLineChart);
		function drawLineChart() {
			var data = google.visualization.arrayToDataTable([
			  ['Days', 'Temperature'],
			  ['1',  99],
			  ['2',  100],
			  ['3',  101],
			  ['4',  100]
			]);
	
			var options = {
			  title: 'Patient Body Temp',
			  curveType: 'function',
			  legend: { position: 'bottom' }
			};
	
			var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
	
			chart.draw(data, options);
		  }
});