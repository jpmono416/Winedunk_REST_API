var slider = document.getElementById('slider');
var range_all_sliders = {
	'min': [  0 ],
	'max': [ 100 ]
};
noUiSlider.create(slider, {
	start: [0, 100],
	step: 1,
    tooltips: [true, true],
	connect: true,
	range: range_all_sliders
});

var minPrice = document.getElementById('minPrice');
var maxPrice = document.getElementById('maxPrice');

slider.noUiSlider.on('update', function( values, handle ) {

	var value = values[handle];

	if ( handle ) {
		maxPrice.value = value;
	} else {
		minPrice.value = value;
	}
});

minPrice.addEventListener('change', function(){
	slider.noUiSlider.set([this.value, null]);
});

maxPrice.addEventListener('change', function(){
	slider.noUiSlider.set([null, this.value]);
});