let tipoConexion;
$(document).ready(function () {
  $("#nombre").focus();
  llenarSelect();
});

function llenarSelect() {
  $.ajax({
    url: "http://localhost:8090/conexiondered/all",
    type: "GET",
    dataType: "json",
    success: function (respuesta) {
      $("#conexionA").empty();
      let opciones = `<option value="">Seleccione una conexion</option>`;
      respuesta.forEach((element) => {
        opciones += `
                            <option value="${element.tipo}">${element.nombre}</option>
                            `;
      });
      $("#conexionA").append(opciones);
    },
    error: function () {
      alert("Ocurrio un error");
    },
  });
}

$("#delectronico").click(function () {
  consultarTodo();
});

function consultarTodo() {
  $.ajax({
    url: "http://localhost:8090/dispositivoelectronico/all",
    type: "GET",
    dataType: "json",
    success: function (respuesta) {
      $("#pintarTabla").empty();
      if (respuesta.length != 0) {
        pintarRespuesta(respuesta);
      }
    },
    error: function () {
      alert("Ocurrio un error");
    },
  });
}

function pintarRespuesta(respuesta) {
  let myTable = `<table class="table table-info table-striped text-center align-middle">
                            <thead>
                                <tr>
                                <th scope="col">Accion</th>
                                <th scope="col">Tipo</th>
                                <th scope="col">Conectado</th>
                                <th scope="col">IP Asignada</th>
                                <th scope="col">Conexion Actual</th>
                                </tr>
                            </thead>`;
  for (i = 0; i < respuesta.length; i++) {
    myTable += `
            <tr>
                <td><button class="btn btn-danger" onclick="borrar('${respuesta[i].mac}')"><i class="bi bi-trash me-1"></i>Borrar</button>
                <button class="btn btn-warning" onclick="editar('${respuesta[i].mac}')"><i class="bi bi-pen me-1"></i>Editar</button></td>
                <td>${respuesta[i].tipo}</td>`;
    if (respuesta[i].conectadoActualmente == true) {
      myTable += `<td>Si</td>`;
    } else {
      myTable += `<td>No</td>`;
    }
    myTable += `
                <td>${respuesta[i].ipAsignada}</td>
                <td>${respuesta[i].conexionActual.tipo}</td>
            </tr>
            `;
  }
  myTable += `</table>`;
  $("#pintarTabla").append(myTable);
}

function borrar(mac) {
  console.log(mac);
  $.ajax({
    url: `http://localhost:8090/dispositivoelectronico/${mac}`,
    type: "DELETE",
    dataType: "json",
    success: function () {
      alert("Se elimino correctamente");
      consultarTodo();
    },
    error: function () {
      alert("Ocurrio un error");
    },
  });
}

function editar(mac) {
  $.ajax({
    url: `http://localhost:8090/dispositivoelectronico/${mac}`,
    type: "GET",
    dataType: "json",
    success: function (respuesta) {
      $("#agregarDispositivo").addClass("d-none");
      $("#actualizarDispositivo").removeClass("d-none");
      $("#mac").prop("disabled", true);
      $("#mac").val(respuesta.mac);
      $("#tipo").val(respuesta.tipo);
      if (respuesta.conectadoActualmente == true) {
        $("#si").prop("checked", true);
      } else {
        $("#no").prop("checked", true);
      }
      $("#ipAsignada").val(respuesta.ipAsignada);
      $("#conexionA").val(respuesta.conexionActual.tipo);
    },
    error: function () {
      alert("Ocurrio un error");
    },
  });
}

$("#enviarDispositivo").submit(function (event) {
  event.preventDefault();
  let id = $("#conexionA").val();
  $.ajax({
    url: `http://localhost:8090/dispositivoelectronico/filter/${id}`,
    type: "GET",
    dataType: "json",
    success: function (respuesta) {
      let maximo = 0;
      respuesta.forEach((element) => {
        if (element.conexionActual.tipo == $("#conexionA").val()) {
          maximo++;
        }
      });
      if (maximo < 3) {
        $.ajax({
          url: `http://localhost:8090/dispositivoelectronico/save`,
          data: JSON.stringify({
            mac: $("#mac").val(),
            tipo: $("#tipo").val(),
            conectadoActualmente: $("input:radio[name=RadioOp]:checked").val(),
            ipAsignada: $("#ipAsignada").val(),
            conexionActual: {
              tipo: $("#conexionA").val(),
            },
          }),
          type: "POST",
          contentType: "application/json",
          dataType: "json",
          error: function (xhr) {
            alert("Ocurrio un error, " + xhr.status);
            console.log(xhr);
          },
          success: function (respuesta) {
            if (respuesta == null) {
              alert(
                "No se pudo registrar porque ya hay un registro con esta mac"
              );
              $("#mac").focus();
            } else {
              alert("Se registro correctamente");
              $("#mac").val("");
              $("#tipo").val("");
              $("#ipAsignada").val("");
              $("#conexionA").val("");
              $("#mac").focus();
              consultarTodo();
            }
          },
        });
      } else {
        alert(
          "No se pudo registrar porque ya hay 3 dispositivos conectados a esta red"
        );
      }
    },
    error: function () {
      alert("Ocurrio un error");
    },
  });
});

$("#actualizarDispositivo").click(function (event) {
  event.preventDefault();
  let datos = {
    mac: $("#mac").val(),
    tipo: $("#tipo").val(),
    conectadoActualmente: $("input[name='RadioOp']:checked").val(),
    ipAsignada: $("#ipAsignada").val(),
    conexionActual: {
      tipo: $("#conexionA").val(),
    },
  };
  $.ajax({
    url: "http://localhost:8090/dispositivoelectronico/update",
    data: JSON.stringify(datos),
    type: "PUT",
    contentType: "application/json",
    dataType: "json",
    error: function (xhr) {
      alert("Ocurrio un error, " + xhr.status);
    },
    success: function () {
      alert("Se actualizo correctamente");
      $("#agregarDispositivo").removeClass("d-none");
      $("#actualizarDispositivo").addClass("d-none");
      $("#mac").prop("disabled", false);
      $("#mac").val("");
      $("#tipo").val("");
      $("#ipAsignada").val("");
      $("#conexionA").val("");
      $("#mac").focus();
      consultarTodo();
    },
  });
});

$("#conexionRed").click(function () {
  consultarConexiones();
});

function consultarConexiones() {
  $.ajax({
    url: "http://localhost:8090/conexiondered/all",
    type: "GET",
    dataType: "json",
    success: function (respuesta) {
      $("#pintarTablaRed").empty();
      if (respuesta.length != 0) {
        pintarRespuestaConexiones(respuesta);
      }
    },
    error: function () {
      alert("Ocurrio un error");
    },
  });
}

function pintarRespuestaConexiones(respuesta) {
  let myTable = `<table class="table table-info table-striped text-center align-middle">
                            <thead>
                                <tr>
                                <th scope="col">Accion</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Tipo de cifrado</th>
                                <th scope="col">Usuario de conexion</th>
                                <th scope="col">Contrasena de conexion</th>
                                </tr>
                            </thead>`;
  for (i = 0; i < respuesta.length; i++) {
    myTable += `
            <tr>
                <td><button class="btn btn-danger" onclick="borrarConexion(${respuesta[i].tipo})"><i class="bi bi-trash me-1"></i>Borrar</button>
                <button class="btn btn-warning" onclick="editarConexion(${respuesta[i].tipo})"><i class="bi bi-pen me-1"></i>Editar</button></td>
                <td>${respuesta[i].nombre}</td>
                <td>${respuesta[i].tipoDeCifrado}</td>
                <td>${respuesta[i].usuarioConexion}</td>
                <td>${respuesta[i].contrasenaDeConexion}</td>
            </tr>
            `;
  }
  myTable += `</table>`;
  $("#pintarTablaRed").append(myTable);
}

function borrarConexion(tipo) {
  $.ajax({
    url: `http://localhost:8090/conexiondered/${tipo}`,
    type: "DELETE",
    dataType: "json",
    success: function () {
      alert("Se elimino correctamente");
      consultarConexiones();
      llenarSelect();
    },
    error: function () {
      alert("Elimine los dispositivos asociados a esta conexion");
    },
  });
}

$("#enviarConexion").submit(function (event) {
  event.preventDefault();
  $.ajax({
    url: `http://localhost:8090/conexiondered/save`,
    data: JSON.stringify({
      nombre: $("#nombre").val(),
      tipoDeCifrado: $("#tipoDeCifrado").val(),
      usuarioConexion: $("#usuarioConexion").val(),
      contrasenaDeConexion: $("#contrasenaDeConexion").val(),
    }),
    type: "POST",
    contentType: "application/json",
    dataType: "json",
    error: function (xhr) {
      alert("Ocurrio un error, " + xhr.status);
    },
    success: function (respuesta) {
      alert("Se registro correctamente");
      $("#nombre").val("");
      $("#tipoDeCifrado").val("");
      $("#usuarioConexion").val("");
      $("#contrasenaDeConexion").val("");
      $("#nombre").focus();
      consultarConexiones();
      llenarSelect();
    },
  });
});

function editarConexion(tipo) {
  tipoConexion = tipo;
  $.ajax({
    url: `http://localhost:8090/conexiondered/${tipo}`,
    type: "GET",
    dataType: "json",
    success: function (respuesta) {
      $("#agregarConexion").addClass("d-none");
      $("#actualizarConexion").removeClass("d-none");
      $("#nombre").val(respuesta.nombre);
      $("#tipoDeCifrado").val(respuesta.tipoDeCifrado);
      $("#usuarioConexion").val(respuesta.usuarioConexion);
      $("#contrasenaDeConexion").val(respuesta.contrasenaDeConexion);
    },
    error: function () {
      alert("Ocurrio un error");
    },
  });
}

$("#actualizarConexion").click(function (event) {
  event.preventDefault();
  let datos = {
    tipo: tipoConexion,
    nombre: $("#nombre").val(),
    tipoDeCifrado: $("#tipoDeCifrado").val(),
    usuarioConexion: $("#usuarioConexion").val(),
    contrasenaDeConexion: $("#contrasenaDeConexion").val(),
  };
  $.ajax({
    url: "http://localhost:8090/conexiondered/update",
    data: JSON.stringify(datos),
    type: "PUT",
    contentType: "application/json",
    dataType: "json",
    error: function (xhr) {
      alert("Ocurrio un error, " + xhr.status);
    },
    success: function () {
      alert("Se actualizo correctamente");
      $("#agregarConexion").removeClass("d-none");
      $("#actualizarConexion").addClass("d-none");
      $("#nombre").val("");
      $("#tipoDeCifrado").val("");
      $("#usuarioConexion").val("");
      $("#contrasenaDeConexion").val("");
      $("#nombre").focus();
      consultarConexiones();
      llenarSelect();
    },
  });
});

const darkModeSlider = document.querySelector("#dark-mode-slider");
darkModeSlider.addEventListener("input", () => {
  const body = document.querySelector("body");
  if (darkModeSlider.value > 0) {
    body.classList.add("dark-mode");
  } else {
    body.classList.remove("dark-mode");
  }
});
