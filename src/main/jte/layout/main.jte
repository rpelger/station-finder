@import gg.jte.Content

@param Content head
@param Content page
@param Content afterBody


<html lang="en">
<head>
    <title>Station Finder</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" href="https://unpkg.com/open-props"/>
    <link rel="stylesheet" href="https://unpkg.com/open-props/normalize.min.css"/>
    <link rel="stylesheet" href="https://unpkg.com/open-props/colors-hsl.min.css"/>


    <script src="https://unpkg.com/htmx.org@2.0.4"></script>

    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

    <link rel="stylesheet" href="/css/app.css"/>
    ${head}
</head>

<body>
<header>
    <div class="logo">
        <span>Station<br>Finder</span>
    </div>
    <div class="menu-icon">
        <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="100" height="100" viewBox="0 0 50 50">
            <path d="M 0 9 L 0 11 L 50 11 L 50 9 Z M 0 24 L 0 26 L 50 26 L 50 24 Z M 0 39 L 0 41 L 50 41 L 50 39 Z"></path>
        </svg>
    </div>
</header>
<main>
    ${page}
</main>
</body>
    ${afterBody}
</html>