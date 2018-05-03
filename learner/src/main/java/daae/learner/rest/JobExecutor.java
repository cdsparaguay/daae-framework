package daae.learner.rest;

import daae.learner.service.JobExecutorService;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.TrainerDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

@RestController
@Api(value = "Trainer", description = "Train prediction models")
@RequestMapping("/train")
public class JobExecutor {
    private final JobExecutorService sparkService;

    @Autowired
    public JobExecutor(JobExecutorService sparkService) {
        this.sparkService = sparkService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addJob(@RequestBody TrainerDTO trainerDTO){
        System.out.println(sparkService.addJob(trainerDTO));
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public String getDataTMP() {
        return "[\n" +
                " {\"cantidad\":1,\"anio\":\"2009\",\"mes\":1,\"dia\":12,\"semana\":\"2\",\"region\":\"AMERICA DEL SUR\",\"pais\":\"PARAGUAY\",\"adm1_nombre\":\"CONCEPCION\",\"adm2_nombre\":\"YBY YAU\",\"adm3_nombre\":\"TAPYTANGUA\",\"sexo\":\"MASCULINO\",\"grupo_edad\":\"20 - 59\",\"estado_final\":\"CONFIRMADO\",\"clasificacion_clinica\":\"A\",\"serotipo\":null,\"origen\":\"AUTOCTONO\",\"id\":30889,\"adm1_codigo\":\"1\",\"adm2_codigo\":\"7\",\"adm3_codigo\":null,\"fuente\":\"Ministerio de Salud Pública y Bienestar Social del Paraguay\"}\n" +
                ", {\"cantidad\":1,\"anio\":\"2009\",\"mes\":4,\"dia\":15,\"semana\":\"15\",\"region\":\"AMERICA DEL SUR\",\"pais\":\"PARAGUAY\",\"adm1_nombre\":\"ASUNCION\",\"adm2_nombre\":\"ASUNCION\",\"adm3_nombre\":\"SD\",\"sexo\":\"MASCULINO\",\"grupo_edad\":\"10 - 19\",\"estado_final\":\"EN ESTUDIO\",\"clasificacion_clinica\":\"B\",\"serotipo\":null,\"origen\":\"AUTOCTONO\",\"id\":66149,\"adm1_codigo\":\"0\",\"adm2_codigo\":\"0\",\"adm3_codigo\":null,\"fuente\":\"Ministerio de Salud Pública y Bienestar Social del Paraguay\"}\n" +
                ", {\"cantidad\":1,\"anio\":\"2009\",\"mes\":4,\"dia\":3,\"semana\":\"13\",\"region\":\"AMERICA DEL SUR\",\"pais\":\"PARAGUAY\",\"adm1_nombre\":\"ASUNCION\",\"adm2_nombre\":\"ASUNCION\",\"adm3_nombre\":\"REPUBLICANO\",\"sexo\":\"MASCULINO\",\"grupo_edad\":\"10 - 19\",\"estado_final\":\"EN ESTUDIO\",\"clasificacion_clinica\":\"B\",\"serotipo\":null,\"origen\":\"AUTOCTONO\",\"id\":135776,\"adm1_codigo\":\"0\",\"adm2_codigo\":\"0\",\"adm3_codigo\":\"28\",\"fuente\":\"Ministerio de Salud Pública y Bienestar Social del Paraguay\"}\n" +
                ", {\"cantidad\":1,\"anio\":\"2013\",\"mes\":5,\"dia\":16,\"semana\":\"20\",\"region\":\"AMERICA DEL SUR\",\"pais\":\"PARAGUAY\",\"adm1_nombre\":\"CENTRAL\",\"adm2_nombre\":\"FERNANDO DE LA MORA\",\"adm3_nombre\":\"DOMINGO SAVIO\",\"sexo\":\"MASCULINO\",\"grupo_edad\":\"10 - 19\",\"estado_final\":\"CONFIRMADO\",\"clasificacion_clinica\":\"SD\",\"serotipo\":null,\"origen\":\"AUTOCTONO\",\"id\":194496,\"adm1_codigo\":\"11\",\"adm2_codigo\":\"3\",\"adm3_codigo\":\"8\",\"fuente\":\"Ministerio de Salud Pública y Bienestar Social del Paraguay\"}\n" +
                ", {\"cantidad\":1,\"anio\":\"2009\",\"mes\":6,\"dia\":2,\"semana\":\"22\",\"region\":\"AMERICA DEL SUR\",\"pais\":\"PARAGUAY\",\"adm1_nombre\":\"CENTRAL\",\"adm2_nombre\":\"ITA\",\"adm3_nombre\":\"VILLA ROSANA\",\"sexo\":\"MASCULINO\",\"grupo_edad\":\"20 - 59\",\"estado_final\":\"CONFIRMADO\",\"clasificacion_clinica\":\"B\",\"serotipo\":null,\"origen\":\"AUTOCTONO\",\"id\":31039,\"adm1_codigo\":\"11\",\"adm2_codigo\":\"5\",\"adm3_codigo\":null,\"fuente\":\"Ministerio de Salud Pública y Bienestar Social del Paraguay\"}\n" +
                ", {\"cantidad\":1,\"anio\":\"2010\",\"mes\":1,\"dia\":5,\"semana\":\"1\",\"region\":\"AMERICA DEL SUR\",\"pais\":\"PARAGUAY\",\"adm1_nombre\":\"ALTO PARAGUAY\",\"adm2_nombre\":\"LA VICTORIA\",\"adm3_nombre\":\"CARMELO PERALTA\",\"sexo\":\"MASCULINO\",\"grupo_edad\":\"20 - 59\",\"estado_final\":\"CONFIRMADO\",\"clasificacion_clinica\":\"B\",\"serotipo\":null,\"origen\":\"AUTOCTONO\",\"id\":31044,\"adm1_codigo\":\"17\",\"adm2_codigo\":\"2\",\"adm3_codigo\":null,\"fuente\":\"Ministerio de Salud Pública y Bienestar Social del Paraguay\"}\n" +
                ", {\"cantidad\":1,\"anio\":\"2010\",\"mes\":1,\"dia\":5,\"semana\":\"1\",\"region\":\"AMERICA DEL SUR\",\"pais\":\"PARAGUAY\",\"adm1_nombre\":\"ALTO PARAGUAY\",\"adm2_nombre\":\"LA VICTORIA\",\"adm3_nombre\":\"CARMELO PERALTA\",\"sexo\":\"MASCULINO\",\"grupo_edad\":\">= 60\",\"estado_final\":\"CONFIRMADO\",\"clasificacion_clinica\":\"B\",\"serotipo\":null,\"origen\":\"AUTOCTONO\",\"id\":31045,\"adm1_codigo\":\"17\",\"adm2_codigo\":\"2\",\"adm3_codigo\":null,\"fuente\":\"Ministerio de Salud Pública y Bienestar Social del Paraguay\"}\n" +
                ", {\"cantidad\":1,\"anio\":\"2011\",\"mes\":2,\"dia\":23,\"semana\":\"8\",\"region\":\"AMERICA DEL SUR\",\"pais\":\"PARAGUAY\",\"adm1_nombre\":\"CENTRAL\",\"adm2_nombre\":\"LAMBARE\",\"adm3_nombre\":\"SANTO DOMINGO\",\"sexo\":\"MASCULINO\",\"grupo_edad\":\"10 - 19\",\"estado_final\":\"CONFIRMADO\",\"clasificacion_clinica\":\"SD\",\"serotipo\":null,\"origen\":\"AUTOCTONO\",\"id\":209767,\"adm1_codigo\":\"11\",\"adm2_codigo\":\"7\",\"adm3_codigo\":\"3\",\"fuente\":\"Ministerio de Salud Pública y Bienestar Social del Paraguay\"}\n" +
                ", {\"cantidad\":1,\"anio\":\"2013\",\"mes\":4,\"dia\":15,\"semana\":\"16\",\"region\":\"AMERICA DEL SUR\",\"pais\":\"PARAGUAY\",\"adm1_nombre\":\"CENTRAL\",\"adm2_nombre\":\"LAMBARE\",\"adm3_nombre\":\"SANTO DOMINGO\",\"sexo\":\"MASCULINO\",\"grupo_edad\":\"10 - 19\",\"estado_final\":\"CONFIRMADO\",\"clasificacion_clinica\":\"SD\",\"serotipo\":null,\"origen\":\"AUTOCTONO\",\"id\":209725,\"adm1_codigo\":\"11\",\"adm2_codigo\":\"7\",\"adm3_codigo\":\"3\",\"fuente\":\"Ministerio de Salud Pública y Bienestar Social del Paraguay\"}\n" +
                "]";
    }


}
