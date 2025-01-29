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

    const infoWindow = new google.maps.InfoWindow({
        content: `
            <h3>${address}</h3>
            <p>Shops! <a href="${infoUrl}" target="_blank">Visit the site</a></p>
        `,
    });

    marker.addListener("click", () => {
        infoWindow.open(map, marker);
    });
}

function fetchBranchData() {
    fetch('/api/branches')
        .then(response => response.json())
        .then(branches => {

            window.initMap = function () {
                const centerPoint = { lat: 42.6977, lng: 23.3219 };
                const map = new google.maps.Map(document.getElementById("map"), {
                    center: centerPoint,
                    zoom: 7,
                });

                branches.forEach(branch => {
                    addBranchMarker(branch.name, branch.lat, branch.lng, branch.infoUrl, map);
                });
            };

            const apiKey = "";
            const script = document.createElement('script');
            script.src = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap`;
            script.async = true;
            script.defer = true;
            document.head.appendChild(script);
        })
        .catch(error => console.error('Error fetching branch data:', error));
}

fetchBranchData();

