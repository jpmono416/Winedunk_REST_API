$( function() {
	var availableProducers = [
		"Bodegas Vicente Gandia",
		"Bodegas Freixenet",
		"Bodegas Codorniu",
		"Bodegas Torres"
	];
    
	var availableAppellations = [
		"Gen",
		"Freixenet",
		"Codorniu",
		"Torres",
		"Hoya de cadenas"
	];
    
    var availableCountries = [
	    "Argentina",
	    "Australia",
	    "Austria",
	    "Canada",
	    "Chile",
	    "Cyprus",
	    "France",
	    "Germany",
	    "Greece",
	    "Hungary",
	    "Israel",
	    "Italy",
	    "New Zealand",
	    "Portugal",
	    "South Africa",
	    "Spain",
	    "Switzerland",
	    "United Kingdom of Great Britain and Northern",
	    "United States of America (the)",
	    "Uruguay",
    ];
      
    var availableLanguages = [
		"Spanish",
		"English",
		"French",
		"German",
		"Dutch",
		"Italian",
		"Portuguese"
	];
      
    var availableCurrencies = [
    	"Euro",
        "Pound"
    ];
    
    var availableRegions = [
    	"Catamarca",
    	"Jujuy",
    	"La Rioja",
    	"Lam Pampa",
    	"Mendoza",
    	"Rio Negro",
    	"Salta",
    	"San Juan",
    	"Tucumán",
    	"Australian Capital Territory",
    	"New South Wales",
    	"Northern Territory",
    	"Queensland",
    	"South Australia",
    	"Tasmania",
    	"Victoria",
    	"Western Australia",
    	"Bergland",
    	"Wien",
    	"swick",
    	"Newfoundland",
    	"Prince Edward Island",
    	"Quebec",
    	"Bio Bio",
    	"Casablanca",
    	"Curico Valley",
    	"Itata",
    	"Maule",
    	"Rapel",
    	"Santiago",
    	"Limassol",
    	"Alsace",
    	"Beaujolais",
    	"Bordeaux",
    	"Burgundy",
    	"Champagne",
    	"Corsica",
    	"Languedoc-Roussillon",
    	"Loire",
    	"Midi-Pyrenees-Southwest",
    	"Provence",
    	"Rhone",
    	"Savoie & Jura",
    	"Ahr",
    	"Baden",
    	"Baden-Wurttemberg",
    	"Franken",
    	"Hessische Bergstrasse",
    	"Mitelrhein",
    	"Mosel-Saar-Ruwer",
    	"Nahe",
    	"Pfalz",
    	"Rheingau",
    	"Rheinhessen",
    	"Saale-Unstrut",
    	"Sachsen",
    	"Wurttemberg",
    	"Athens",
    	"Chalkis",
    	"Kabala",
    	"Kariani",
    	"Budapest",
    	"Tokaj",
    	"Villany",
    	"Binyamina",
    	"Haute-Judee",
    	"Katzrin",
    	"Merom Hagalil",
    	"Rishon LeZion",
    	"Abruzzi",
    	"Basilicata",
    	"Calabria",
    	"Campania",
    	"Emilia Romagna",
    	"Friuli-Venezia Giulia",
    	"Latium",
    	"Lazio",
    	"Liguria",
    	"Lombardy",
    	"Marches",
    	"Molise",
    	"Northeast",
    	"Piemonte",
    	"Puglia",
    	"Rome",
    	"Sardinia",
    	"Sicily",
    	"Trentino-Alto Adige",
    	"Tuscany",
    	"Umbria",
    	"Valle D´Aosta",
    	"Veneto",
    	"Aukland",
    	"Canterbury",
    	"Central Otago",
    	"Gisborne",
    	"Hawkes Bay",
    	"Marlborough",
    	"Nelson",
    	"New Zealand",
    	"Northland",
    	"Otago",
    	"Waikato/Bay of Plenty",
    	"Wairarapa",
    	"Wellington",
    	"Alentejo",
    	"Douro",
    	"Madiera",
    	"Other Portugal",
    	"Porto",
    	"Vinho Verde",
    	"Constantia",
    	"Other South Africa",
    	"Paarl",
    	"Stellenbosch",
    	"Swartland",
    	"Tulbagh",
    	"Andalucia",
    	"Aragon",
    	"Canary Islands",
    	"Castilla La Mancha",
    	"Castilla y León",
    	"Catalonia",
    	"Extremadura",
    	"Galicia",
    	"Murcia",
    	"Navarra",
    	"Penedes",
    	"Priorat",
    	"Ribera del Duero",
    	"Rioja",
    	"Sherry",
    	"Valenciana",
    	"Aargau",
    	"Lake Geneva",
    	"Neuchatel",
    	"Ticino",
    	"Valais & Vaud",
    	"England",
    	"Ireland",
    	"Scottland",
    	"Wales",
    	"Montevideo",
    	"Alabama",
    	"Alaska",
    	"American Samoa",
    	"Arizona",
    	"Arkansas",
    	"California",
    	"Colorado",
    	"Connecticut",
    	"Delaware",
    	"District of Columbia",
    	"Federated States of Micronesia",
    	"Florida",
    	"Georgia",
    	"Guam",
    	"Hawaii",
    	"Idaho",
    	"Illinois",
    	"Indiana",
    	"Iowa",
    	"Kansas",
    	"Kentucky",
    	"Louisiana",
    	"Maine",
    	"Marshall Islands",
    	"Maryland",
    	"Massachusetts",
    	"Michigan",
    	"Minnesota",
    	"Mississippi",
    	"Missouri",
    	"Montana",
    	"Nebraska",
    	"Nevada",
    	"New Hampshire",
    	"New Jersey",
    	"New Mexico",
    	"New York",
    	"North Carolina",
    	"North Dakota",
    	"Northern Mariana Islands",
    	"Ohio",
    	"Oklahoma",
    	"Oregon",
    	"Palau",
    	"Pennsylvania",
    	"Puerto Rico",
    	"Rhode Island",
    	"South Carolina",
    	"South Dakota",
    	"Tennessee",
    	"Texas",
    	"Utah",
    	"Vermont",
    	"Virgin Island",
    	"Virginia",
    	"Washington",
    	"West Virginia",
    	"Wisconsin",
    	"Wyoming"
    ];
    
    $( "#producer" ).autocomplete({
      source: availableProducers
    });
    
    $( "#appellation" ).autocomplete({
      source: availableAppellations
    });
    
    $( "#country" ).autocomplete({
      source: availableCountries
    });
    
    $( "#language" ).autocomplete({
      source: availableLanguages
    });
    
    $( "#currency" ).autocomplete({
      source: availableCurrencies
    });
    
    $( "#region" ).autocomplete({
        source: availableRegions
      });
    
  } );
