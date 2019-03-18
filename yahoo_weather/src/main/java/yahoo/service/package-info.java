/**
 * Пакет представляет собой сервис для получения прогноза погоды, по названию города, при помощи Yahoo Weather Api
 * {@link yahoo.service.YahooReceiver} Сервис принимает JMS сообщение с названием
 * города, по которому делает запрос погоды
 * {@link yahoo.service.WeatherForecastService} Сервис делает запрос к Yahoo Weather Api,
 * по полученному названию города, и передает результат в виде JMS сообщения.
 */
package yahoo.service;