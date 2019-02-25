package weatherservice.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import weatherservice.service.WeatherService;
import weatherservice.view.WeatherFilter;
import weatherservice.view.WeatherView;

import javax.persistence.NoResultException;

@Controller
public class WeatherController {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(WeatherController.class);
    private final WeatherService service;

    @Autowired
    public WeatherController(WeatherService service) {
        this.service = service;
    }

    /**
     * Ввод информации клиентом, для последующего запроса на получения прогноза погоды
     *
     * @param filter Объект, содержащий информацию необходимую для поиска прогноза в базе данных
     * @return Форма для ввода информации клиентом
     */
    @RequestMapping(value = {"/forecast"}, method = RequestMethod.GET)
    public ModelAndView getForecastFilter(@ModelAttribute("filter") WeatherFilter filter) {
        ModelAndView view = new ModelAndView();
        view.addObject("filter", filter);
        view.addObject("date", filter.date);
        view.addObject("city", filter.city);
        view.setViewName("req");
        return view;
    }

    @ModelAttribute("filter")
    public WeatherFilter createFilter() {
        return new WeatherFilter();
    }

    /**
     * Отображении информации о запрашиваемом прогнозе
     *
     * @param filter Объект, заполненный информацией необходимой для поиска прогноза в базе данных
     * @return Форма для вывода информации о запрашиваемом прогнозе
//     * @throws WeatherBrokerServiceException Исключение, сгенерированное неправильным воодом информации,
     *                                       необходимой для запроса к базе данных
     */
    @RequestMapping(value = "/forecast/submit", method = RequestMethod.POST)
    public ModelAndView getForecast(@ModelAttribute("filter") WeatherFilter filter){
        ModelAndView view = new ModelAndView();
        log.info("Filter is: {}", filter);
        view.setViewName("resp");
        WeatherView weatherView;
        try {
            weatherView = service.getWeatherByFilter(filter);
        } catch (NoResultException e) {
            return new ModelAndView("redirect:/api/weatherbroker/error");
        }
        view.addObject("weatherView", weatherView);
        return view;
    }

    /**
     * Отображение информации об ошибке приложения
     *
     * @return Форма, информирующая об ошибке работы сервиса
     */
    @RequestMapping(value = "/error")
    public ModelAndView error() {
        ModelAndView view = new ModelAndView();
        view.setViewName("error");
        return view;
    }
}
