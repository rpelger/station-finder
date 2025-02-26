<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script>
        const options = {
            enableHighAccuracy: true,
            timeout: 5000,
            maximumAge: 0,
        };

        function success({coords}) {
            @raw
            window.location.href = `/app/stations/near/${coords.latitude}/${coords.longitude}`
            @endraw
        }

        function error(err) {
            console.warn(err)
            window.location.href = "/app/stations/near/48.178745/11.537589"
        }

        navigator.geolocation.getCurrentPosition(success, error, options);
    </script>
</head>
<body></body>
</html>