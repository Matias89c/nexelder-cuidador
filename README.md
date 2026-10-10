# NexElder · Panel del cuidador

Web app para controlar con SMS o WhatsApp el móvil de un familiar que usa NexElder. Los datos (teléfono y PIN) se guardan solo en el dispositivo de quien la usa; la app no envía nada a ningún servidor.

- **Web (iPhone y cualquier navegador):** https://matias89c.github.io/nexelder-cuidador/ — funciona sin conexión una vez abierta.
- **Android:** app nativa en `android/`, que empaqueta esta misma web. El APK release se compila en GitHub Actions (pestaña *Actions → Android APK*).

## Estructura

| Ruta | Qué es |
|---|---|
| `index.html` | Todo el panel: estilos, iconos y lógica |
| `sw.js` | Caché sin conexión de la versión web |
| `fonts/` | Inter (licencia OFL), usada en Android; en iPhone se usa SF Pro |
| `android/` | Proyecto Android (WebView nativa, sin dependencias de red) |

## Firma del APK

El flujo lee estos secretos del repositorio: `NEX_KEYSTORE_B64` (el `.jks` en base64), `NEX_KEYSTORE_PASSWORD`, `NEX_KEY_ALIAS` y `NEX_KEY_PASSWORD`. Sin ellos genera un APK sin firmar. Guarda el keystore en un lugar seguro: sin él no se pueden publicar actualizaciones de la app.
