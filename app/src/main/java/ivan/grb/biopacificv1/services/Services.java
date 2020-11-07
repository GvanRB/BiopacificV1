package ivan.grb.biopacificv1.services;

import java.util.List;

import ivan.grb.biopacificv1.models.Agregarmascota;
import ivan.grb.biopacificv1.models.Clientes;
import ivan.grb.biopacificv1.models.Dataclientemenu;
import ivan.grb.biopacificv1.models.ListadoMascotasPorCliente;
import ivan.grb.biopacificv1.models.Perfiles;
import ivan.grb.biopacificv1.models.ServiciosXmascotas;
import ivan.grb.biopacificv1.models.Solicitarservicio;
import ivan.grb.biopacificv1.models.Usuarios;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Services {
    @GET("api/cargo")
    Call<List<Usuarios>> getUsuario();

    @POST("api/usuario/iniciarsesion")
    Call<Boolean>  postvalidariniciosesion(@Body Usuarios usuario);

    @POST("api/cliente")
    Call<Clientes> postRegistrar(@Body Clientes clientes);

    @POST("api/cliente/usuariocliente")
    Call<Dataclientemenu> postNomVet(@Body Usuarios usuarios);

    @GET("api/usuario/validarcorreo/{correo}")
    Call<Boolean> getValidarCorreo(@Path("correo")String correo);

    @POST("api/mascota/listadomascotasporcliente")
    Call<List<ListadoMascotasPorCliente>> postListMascXCli(@Body Usuarios usuarios);

    @POST("api/mascota")
    Call<Agregarmascota> postAddMascota(@Body Agregarmascota agregarmascota);

    @GET("api/perfil")
    Call<List<Perfiles>> getListPerfiles();

    @POST("api/detalleorden")
    Call<Solicitarservicio> postRegistrarServicio(@Body Solicitarservicio solicitarservicio);

    @GET("api/detalleorden/serviciosmascota/{idmascota}")
    Call<List<ServiciosXmascotas>> getlistadoserviciosmasc(@Path("idmascota") String idmascota);

}
