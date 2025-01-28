// Функция за добавяне на маркер на картата
function addBranchMarker(address, lat, lon, infoUrl, map) {
    const marker = new google.maps.Marker({
        position: { lat, lng: lon },
        map,
        title: address,
        icon: {
            url: "https://maps.google.com/mapfiles/ms/icons/red-dot.png"
        }
    });

    // Информационен прозорец с линк към сайта на магазина
    const infoWindow = new google.maps.InfoWindow({
        content: `
            <h3>${address}</h3>
            <p>Ayiko's shop! <a href="${infoUrl}" target="_blank">Visit the site</a></p>
        `,
    });

    // Показване на информационния прозорец при клик върху маркера
    marker.addListener("click", () => {
        infoWindow.open(map, marker);
    });
}

// Функция за извличане на данни за филиалите от сървъра
function fetchBranchData() {
    fetch('/api/branches')  // Извикване на API, за да вземем данни за филиалите
        .then(response => response.json())
        .then(branches => {
            // Инициализация на картата и добавяне на маркери
            window.initMap = function () {
                const centerPoint = { lat: 42.6977, lng: 23.3219 }; // Център на картата
                const map = new google.maps.Map(document.getElementById("map"), {
                    center: centerPoint,
                    zoom: 7,
                });

                // Добавяне на маркери за всички локации
                branches.forEach(branch => {
                    addBranchMarker(branch.name, branch.lat, branch.lng, branch.infoUrl, map);
                });
            };

            // Зареждаме Google Maps API асинхронно с callback към initMap
            const apiKey = "AIzaSyCGG1gILcgSJVg3YUYcH03E_bM5eVVeTQk";  // Поставете вашия API ключ тук
            const script = document.createElement('script');
            script.src = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap`;
            script.async = true;
            script.defer = true;
            document.head.appendChild(script);
        })
        .catch(error => console.error('Error fetching branch data:', error));
}

// Извикване на функцията за извличане на данни
fetchBranchData();

