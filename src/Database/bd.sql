-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.6.8-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para taller_bd
CREATE DATABASE IF NOT EXISTS `taller_bd` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `taller_bd`;

-- Volcando estructura para tabla taller_bd.ajuste_inventario
CREATE TABLE IF NOT EXISTS `ajuste_inventario` (
  `idajuste` int(11) NOT NULL COMMENT 'Codigo de identificacion del ajuste de inventario',
  `fecha` date NOT NULL COMMENT 'Fecha en que se realiza el ajuste',
  `hora` datetime NOT NULL COMMENT 'Hora en el que se realiza el ajuste',
  `idmotivo` int(11) NOT NULL COMMENT 'Codigo de identificacion del motivo del ajuste',
  `idempleado` int(11) NOT NULL COMMENT 'codigo de identificacion del empleado relacionado al ajuste',
  `iddeposito` int(11) NOT NULL COMMENT 'Codigo de identificacion del deposito al cual se realizara el ajuste',
  `idarticulo` int(11) NOT NULL COMMENT 'Codigo del articulo al cual se realizara el ajuste',
  `cantidad` double NOT NULL COMMENT 'Cantidad que se utilizara para el ajuste',
  `tipo` varchar(25) NOT NULL COMMENT 'Tipo de ajuste, si sera E=ENTRADA o S=SALIDA',
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identificacion del usuario relacionado',
  PRIMARY KEY (`idajuste`),
  KEY `FK_AJUSTE_INVENTARIO_EMPLEADO` (`idempleado`),
  KEY `FK_AJUSTE_INVENTARIO_DEPOSITO` (`iddeposito`),
  KEY `FK_AJUSTE_INVENTARIO_ARTICULO` (`idarticulo`),
  KEY `FK_AJUSTE_INVENRARIO_USUARIO` (`idusuario`),
  KEY `FK_AJUSTE_INVENTARIO_MOTIVO` (`idmotivo`),
  CONSTRAINT `FK_AJUSTE_INVENRARIO_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_AJUSTE_INVENTARIO_ARTICULO` FOREIGN KEY (`idarticulo`) REFERENCES `articulo` (`idarticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_AJUSTE_INVENTARIO_DEPOSITO` FOREIGN KEY (`iddeposito`) REFERENCES `deposito` (`iddeposito`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_AJUSTE_INVENTARIO_EMPLEADO` FOREIGN KEY (`idempleado`) REFERENCES `empleado` (`idempleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_AJUSTE_INVENTARIO_MOTIVO` FOREIGN KEY (`idmotivo`) REFERENCES `motivo` (`idmotivo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de los ajustes de inventario en el sistema\r\n';

-- Volcando datos para la tabla taller_bd.ajuste_inventario: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.apertura
CREATE TABLE IF NOT EXISTS `apertura` (
  `idapertura` int(11) NOT NULL COMMENT 'Codigo de identificacion de la apertura',
  `idcaja` int(11) NOT NULL COMMENT 'Codigo de identificacion de la caja relacionadad',
  `fecha` datetime NOT NULL COMMENT 'Fecha de la apertura',
  `monto_inicial` double(24,4) NOT NULL COMMENT 'Monto inicial con la cual se realiza la apertura de caja',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observaciones relacionadas a la apertura',
  `estado` varchar(1) NOT NULL COMMENT 'Estado de la apertura, A=ABIERTO, C=CERRADO',
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identifiacacion del usuario de la apertura',
  PRIMARY KEY (`idapertura`),
  KEY `FK_APERTURA_CAJA` (`idcaja`),
  KEY `FK_APERTURA_USUARIO` (`idusuario`),
  CONSTRAINT `FK_APERTURA_CAJA` FOREIGN KEY (`idcaja`) REFERENCES `caja` (`idcaja`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_APERTURA_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de las aperturas de caja del sistema';

-- Volcando datos para la tabla taller_bd.apertura: ~0 rows (aproximadamente)
REPLACE INTO `apertura` (`idapertura`, `idcaja`, `fecha`, `monto_inicial`, `observacion`, `estado`, `idusuario`) VALUES
	(1, 1, '2023-02-04 13:34:44', 250000.0000, '', 'A', 1);

-- Volcando estructura para tabla taller_bd.articulo
CREATE TABLE IF NOT EXISTS `articulo` (
  `idarticulo` int(11) NOT NULL COMMENT 'Codigo de indentificacion del articulo',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre del articulo',
  `referencia` varchar(100) DEFAULT NULL COMMENT 'Referencia o algun datos que el articulo tenga visible',
  `estado` varchar(1) NOT NULL DEFAULT 'A' COMMENT 'Estado correspondiente del articulo. A=Activo y I=Inactivo',
  `idmarca` int(11) NOT NULL COMMENT 'Codigo de identificacion de la marca relacionada',
  `idlinea` int(11) NOT NULL COMMENT 'Codigo de identificacion de la linea relacionada',
  `idunidad` int(11) NOT NULL COMMENT 'Codigo de identificacion de la unidad de medida relacionada',
  `idtipo` int(11) NOT NULL COMMENT 'Codigo de identificacion del tipo de articulo relacionado',
  `costo` double(24,4) NOT NULL COMMENT 'Costo del articulo',
  `precio` double(24,4) NOT NULL COMMENT 'Precio de venta del articulo',
  `idimpuesto` int(11) NOT NULL COMMENT 'Codigo de identificacion del impuesto del articulo',
  PRIMARY KEY (`idarticulo`),
  KEY `FK_ARTICULO_MARCA` (`idmarca`),
  KEY `FK_ARTICULO_LINEA` (`idlinea`),
  KEY `FK_ARTICULO_UNIDAD_MEDIDA` (`idunidad`),
  KEY `FK_ARTICULO_TIPO_ARTICULO` (`idtipo`),
  KEY `FK_ARTICULO_IMPUESTO` (`idimpuesto`),
  CONSTRAINT `FK_ARTICULO_IMPUESTO` FOREIGN KEY (`idimpuesto`) REFERENCES `impuesto` (`idimpuesto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ARTICULO_LINEA` FOREIGN KEY (`idlinea`) REFERENCES `linea` (`idlinea`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ARTICULO_MARCA` FOREIGN KEY (`idmarca`) REFERENCES `marca` (`idmarca`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ARTICULO_TIPO_ARTICULO` FOREIGN KEY (`idtipo`) REFERENCES `tipo_articulo` (`idtipo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ARTICULO_UNIDAD_MEDIDA` FOREIGN KEY (`idunidad`) REFERENCES `unidad_medida` (`idunidad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de los articulos que seran utilizadas en los modulos correspondientes de facturacion, compra y servicios';

-- Volcando datos para la tabla taller_bd.articulo: ~3 rows (aproximadamente)
REPLACE INTO `articulo` (`idarticulo`, `descripcion`, `referencia`, `estado`, `idmarca`, `idlinea`, `idunidad`, `idtipo`, `costo`, `precio`, `idimpuesto`) VALUES
	(1, 'COCA COLA 1LTS', '4152859666', 'A', 1, 1, 1, 1, 7500.0000, 10000.0000, 3),
	(2, 'LAVADO DE AUTO', 'LVA', 'A', 1, 1, 1, 2, 0.0000, 30000.0000, 1),
	(3, 'YERBA MATE CAMPESINO', 'CAMPESINO1KG', 'A', 1, 1, 1, 1, 6500.0000, 9000.0000, 3),
	(4, 'ARITULO DE PRUEBA', 'prd', 'A', 3, 1, 1, 1, 15000.0000, 20000.0000, 1);

-- Volcando estructura para tabla taller_bd.articulo_deposito
CREATE TABLE IF NOT EXISTS `articulo_deposito` (
  `idarticulo` int(11) NOT NULL COMMENT 'Codigo de identificacion del articulo relacionado',
  `iddeposito` int(11) NOT NULL COMMENT 'Codigo de identificacion del deposito relacionado',
  `existencia` double(24,4) NOT NULL COMMENT 'Existencia o cantidad el articulo en el deposito',
  PRIMARY KEY (`idarticulo`,`iddeposito`),
  KEY `FK_ARTICULO_DEPOSITO_DEPOSITO` (`iddeposito`),
  KEY `FK_ARTICULO_DEPOSITO_ARTICULO` (`idarticulo`),
  CONSTRAINT `FK_ARTICULO_DEPOSITO_ARTICULO` FOREIGN KEY (`idarticulo`) REFERENCES `articulo` (`idarticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ARTICULO_DEPOSITO_DEPOSITO` FOREIGN KEY (`iddeposito`) REFERENCES `deposito` (`iddeposito`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacena los datos de los articulos y los depositos, donde estan las existencias';

-- Volcando datos para la tabla taller_bd.articulo_deposito: ~3 rows (aproximadamente)
REPLACE INTO `articulo_deposito` (`idarticulo`, `iddeposito`, `existencia`) VALUES
	(1, 1, -9.0000),
	(3, 1, 40.0000),
	(4, 1, -1.0000);

-- Volcando estructura para tabla taller_bd.barrio
CREATE TABLE IF NOT EXISTS `barrio` (
  `idbarrio` int(11) NOT NULL COMMENT 'Codigo de identificacion del barrio',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre del barrio',
  `idciudad` int(11) NOT NULL COMMENT 'Codigo de identificacion de la ciudad relacionada',
  PRIMARY KEY (`idbarrio`),
  KEY `FK_BARRIO_CIUDAD` (`idciudad`),
  CONSTRAINT `FK_BARRIO_CIUDAD` FOREIGN KEY (`idciudad`) REFERENCES `ciudad` (`idciudad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los barrios de las ciudades';

-- Volcando datos para la tabla taller_bd.barrio: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.caja
CREATE TABLE IF NOT EXISTS `caja` (
  `idcaja` int(11) NOT NULL COMMENT 'Codigo de identificacion de la caja',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre de la caja',
  PRIMARY KEY (`idcaja`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de las cajas que se utilizaran para las aperturas y cierres de caja';

-- Volcando datos para la tabla taller_bd.caja: ~2 rows (aproximadamente)
REPLACE INTO `caja` (`idcaja`, `descripcion`) VALUES
	(1, 'CAJA SALON 1'),
	(2, 'CAJA SALON 2');

-- Volcando estructura para tabla taller_bd.cierre
CREATE TABLE IF NOT EXISTS `cierre` (
  `idcierre` int(11) NOT NULL COMMENT 'Codigo de identificacion del cierre de la apertura',
  `idapertura` int(11) NOT NULL COMMENT 'Codigo de identificacion de la apertura de caja relacionada',
  `fecha` datetime NOT NULL COMMENT 'Fecha y hora del cierre de la apertura de caja',
  `monto_cierre` double(24,4) NOT NULL COMMENT 'Monto con el cual se realizo el cierre de la apertura de caja',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observaciones relacioandas al cierre de la apertura',
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identificacion del usuario del cierre',
  PRIMARY KEY (`idcierre`,`idapertura`),
  KEY `FK_CIERRE_APERTURA` (`idapertura`),
  KEY `FK_CIERRE_USUARIO` (`idusuario`),
  CONSTRAINT `FK_CIERRE_APERTURA` FOREIGN KEY (`idapertura`) REFERENCES `apertura` (`idapertura`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_CIERRE_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los cierres de las aperturas de caja del sistema';

-- Volcando datos para la tabla taller_bd.cierre: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.ciudad
CREATE TABLE IF NOT EXISTS `ciudad` (
  `idciudad` int(11) NOT NULL COMMENT 'Codigo de identificacion de la ciudad',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre de la ciudad',
  `idpais` int(11) NOT NULL COMMENT 'Codigo de identificacion del pais relacionado a la ciudad',
  PRIMARY KEY (`idciudad`),
  KEY `FK_CIUDAD_PAIS` (`idpais`),
  CONSTRAINT `FK_CIUDAD_PAIS` FOREIGN KEY (`idpais`) REFERENCES `pais` (`idpais`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados de las ciudades de cada pais';

-- Volcando datos para la tabla taller_bd.ciudad: ~2 rows (aproximadamente)
REPLACE INTO `ciudad` (`idciudad`, `descripcion`, `idpais`) VALUES
	(1, 'CAAGUAZU', 1),
	(2, 'DR. J. EULOGIO ESTIGARRIBIA', 1);

-- Volcando estructura para tabla taller_bd.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `idcliente` int(11) NOT NULL COMMENT 'Codigo de identificacion del cliente',
  `nombre` varchar(100) NOT NULL COMMENT 'Nombre del cliente',
  `apellido` varchar(100) NOT NULL COMMENT 'Apellido del cliente',
  `cedula` varchar(25) NOT NULL COMMENT 'Cedula de identidad del cliente',
  `ruc` varchar(25) DEFAULT NULL COMMENT 'Registro unico del contribuyente del cliente si es que posee',
  `telefono` varchar(15) DEFAULT NULL COMMENT 'Telefono del cliente',
  `direccion` varchar(250) DEFAULT NULL COMMENT 'Direccion de domicilio del cliente',
  `estado` varchar(1) NOT NULL DEFAULT 'A' COMMENT 'Estado del cliente, A=Activo y I=Inactivo',
  `idciudad` int(11) NOT NULL COMMENT 'Codigo de identificacion de la ciudad relacionadad',
  PRIMARY KEY (`idcliente`),
  UNIQUE KEY `cedula` (`cedula`),
  KEY `FK_CLIENTE_CIUDAD` (`idciudad`),
  CONSTRAINT `FK_CLIENTE_CIUDAD` FOREIGN KEY (`idciudad`) REFERENCES `ciudad` (`idciudad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacena los datos relacionados a los clientes';

-- Volcando datos para la tabla taller_bd.cliente: ~2 rows (aproximadamente)
REPLACE INTO `cliente` (`idcliente`, `nombre`, `apellido`, `cedula`, `ruc`, `telefono`, `direccion`, `estado`, `idciudad`) VALUES
	(1, 'ARMANDO ARIEL', 'PERALTA MARTINEZ', '5955455', '5955455-0', '0975489075', 'EX CAMPO 9 - DR. J.EULOGIO ESTIGARRIBIA', 'A', 1),
	(2, 'ARIEL', 'MARTINEZ', '5955456', '5955456-1', '0975489098', 'EX CAMPO 9 - DR. J.EULOGIO ESTIGARRIBIA', 'A', 1);

-- Volcando estructura para tabla taller_bd.compra
CREATE TABLE IF NOT EXISTS `compra` (
  `idcompra` int(11) NOT NULL COMMENT 'Codigo de identificacion de la compra',
  `numero_documento` varchar(24) NOT NULL COMMENT 'Numero de documento o comprobante de compra',
  `numero_timbrado` int(11) NOT NULL COMMENT 'Numero de timbrado del documento de compra',
  `fecha` date NOT NULL COMMENT 'Fecha del documento de compras',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observaciones referente a la compra',
  `idproveedor` int(11) NOT NULL COMMENT 'Codigo de identificacion del proveedor de la compra',
  `iddeposito` int(11) NOT NULL COMMENT 'Codigo de identificacion del deposito de la compra',
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identificacion del usuario que ingreso la compra',
  PRIMARY KEY (`idcompra`),
  KEY `FK_COMPRA_PROVEEDOR` (`idproveedor`),
  KEY `FK_COMPRA_DEPOSITO` (`iddeposito`),
  KEY `FK_COMPRA_USUARIO` (`idusuario`),
  CONSTRAINT `FK_COMPRA_DEPOSITO` FOREIGN KEY (`iddeposito`) REFERENCES `deposito` (`iddeposito`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_COMPRA_PROVEEDOR` FOREIGN KEY (`idproveedor`) REFERENCES `proveedor` (`idproveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_COMPRA_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos principales de las compras en el sistema';

-- Volcando datos para la tabla taller_bd.compra: ~0 rows (aproximadamente)
REPLACE INTO `compra` (`idcompra`, `numero_documento`, `numero_timbrado`, `fecha`, `observacion`, `idproveedor`, `iddeposito`, `idusuario`) VALUES
	(1, '001-001-0000125', 11223344, '2023-02-05', 'PRUEBA DE COMPRAS DEL SISTEMA', 1, 1, 1);

-- Volcando estructura para tabla taller_bd.compra_detalle
CREATE TABLE IF NOT EXISTS `compra_detalle` (
  `idcompra` int(11) NOT NULL COMMENT 'Codigo de identificacion de la compra relacionada',
  `idarticulo` int(11) NOT NULL COMMENT 'Codigo de identificacion del articulo relacionado',
  `numero_item` int(11) NOT NULL COMMENT 'Numero de item al momento de ingresar la compra',
  `cantidad` double(24,4) NOT NULL COMMENT 'Cantidad del articulo correspondiente de la compra',
  `costo` double(24,4) NOT NULL COMMENT 'Costo correspondiente del articulo de compra',
  PRIMARY KEY (`idcompra`,`idarticulo`),
  KEY `FK_COMPRA_DETALLE_ARTICULO` (`idarticulo`),
  KEY `FK_COMPRA_DETALLE_COMPRA` (`idcompra`),
  CONSTRAINT `FK_COMPRA_DETALLE_ARTICULO` FOREIGN KEY (`idarticulo`) REFERENCES `articulo` (`idarticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_COMPRA_DETALLE_COMPRA` FOREIGN KEY (`idcompra`) REFERENCES `compra` (`idcompra`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los detalles de los articulos que corresponden a la compra';

-- Volcando datos para la tabla taller_bd.compra_detalle: ~2 rows (aproximadamente)
REPLACE INTO `compra_detalle` (`idcompra`, `idarticulo`, `numero_item`, `cantidad`, `costo`) VALUES
	(1, 1, 1, 25.0000, 7500.0000),
	(1, 3, 2, 50.0000, 6500.0000);

-- Volcando estructura para tabla taller_bd.compra_forma_pago
CREATE TABLE IF NOT EXISTS `compra_forma_pago` (
  `idcompra` int(11) NOT NULL COMMENT 'Codigo de identificacion de la compra',
  `idforma` int(11) NOT NULL COMMENT 'Codigo de identificacion de la forma de pago',
  PRIMARY KEY (`idcompra`,`idforma`),
  KEY `FK_COMPRA_FORMA_PAGO_FORMA_PAGO` (`idforma`),
  KEY `FK_COMPRA_FORMA_PAGO_COMPRA` (`idcompra`),
  CONSTRAINT `FK_COMPRA_FORMA_PAGO_COMPRA` FOREIGN KEY (`idcompra`) REFERENCES `compra` (`idcompra`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_COMPRA_FORMA_PAGO_FORMA_PAGO` FOREIGN KEY (`idforma`) REFERENCES `forma_pago` (`idforma`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara las relacion entre compras y formas de pago';

-- Volcando datos para la tabla taller_bd.compra_forma_pago: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.configuracion
CREATE TABLE IF NOT EXISTS `configuracion` (
  `idconfiguracion` int(11) NOT NULL COMMENT 'Codigo de identificacion de la configuracion',
  `tipo_articulo_servicio` int(11) NOT NULL COMMENT 'Codigo de identificacion para el tipo de articulo servicios, para que no mueva existencias',
  `cantidad_maxima_facturacion` int(11) NOT NULL COMMENT 'Cantidad maxima de items que pueden ser facturados por operacion',
  `facturacion_timbrado` int(11) NOT NULL COMMENT 'Codigo de identificacion del timbrado asignado para facturaciones y servicios',
  `nota_credito_timbrado` int(11) NOT NULL COMMENT 'Codigo de identificacion del timbrado asignado para Notas de credito',
  `nota_debito_timbrado` int(11) NOT NULL COMMENT 'Codigo de identificacion del timbrado asignado para Notas de debito',
  PRIMARY KEY (`idconfiguracion`),
  KEY `FK_CONFIGURACION_FACTURACION_TIMBRADO` (`facturacion_timbrado`),
  KEY `FK_CONFIGURACION_NOTA_CREDITO_TIMBRADO` (`nota_credito_timbrado`),
  KEY `FK_CONFIGURACION_TIPO_ARTICULO` (`tipo_articulo_servicio`),
  KEY `FK_CONFIGURACION_NOTA_DEBITO_TIMBRADO` (`nota_debito_timbrado`),
  CONSTRAINT `FK_CONFIGURACION_FACTURACION_TIMBRADO` FOREIGN KEY (`facturacion_timbrado`) REFERENCES `timbrado` (`idtimbrado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_CONFIGURACION_NOTA_CREDITO_TIMBRADO` FOREIGN KEY (`nota_credito_timbrado`) REFERENCES `timbrado` (`idtimbrado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_CONFIGURACION_NOTA_DEBITO_TIMBRADO` FOREIGN KEY (`nota_debito_timbrado`) REFERENCES `timbrado` (`idtimbrado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_CONFIGURACION_TIPO_ARTICULO` FOREIGN KEY (`tipo_articulo_servicio`) REFERENCES `tipo_articulo` (`idtipo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara configuraciones de todo tipo para el funcionamiento del sistema';

-- Volcando datos para la tabla taller_bd.configuracion: ~1 rows (aproximadamente)
REPLACE INTO `configuracion` (`idconfiguracion`, `tipo_articulo_servicio`, `cantidad_maxima_facturacion`, `facturacion_timbrado`, `nota_credito_timbrado`, `nota_debito_timbrado`) VALUES
	(1, 2, 100, 1, 2, 3);

-- Volcando estructura para tabla taller_bd.deposito
CREATE TABLE IF NOT EXISTS `deposito` (
  `iddeposito` int(11) NOT NULL COMMENT 'Codigo de identificacion del deposito',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre del deposito',
  PRIMARY KEY (`iddeposito`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los depositos, en las cuales se almacenaran las existencia de los articulos';

-- Volcando datos para la tabla taller_bd.deposito: ~0 rows (aproximadamente)
REPLACE INTO `deposito` (`iddeposito`, `descripcion`) VALUES
	(1, 'DEPOSITO CENTRAL');

-- Volcando estructura para tabla taller_bd.empleado
CREATE TABLE IF NOT EXISTS `empleado` (
  `idempleado` int(11) NOT NULL COMMENT 'Codigo de identificacion del empleado',
  `nombre` varchar(100) NOT NULL COMMENT 'Nombre del empleado',
  `apellido` varchar(100) NOT NULL COMMENT 'Apellido del empleado',
  `cedula` varchar(25) NOT NULL COMMENT 'Cedula de identidad del empleado',
  `telefono` varchar(25) DEFAULT NULL COMMENT 'Telefono o celular del empleado',
  `direccion` varchar(250) DEFAULT NULL COMMENT 'Direccion de domicilio del empleado',
  `estado` varchar(1) NOT NULL COMMENT 'Estado del empleado, A=Activo y I=Inactivo',
  `idciudad` int(11) NOT NULL COMMENT 'Codigo de identificacion de la ciudad',
  PRIMARY KEY (`idempleado`),
  KEY `FK_EMPLEADO_CIUDAD` (`idciudad`),
  CONSTRAINT `FK_EMPLEADO_CIUDAD` FOREIGN KEY (`idciudad`) REFERENCES `ciudad` (`idciudad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los empleados';

-- Volcando datos para la tabla taller_bd.empleado: ~2 rows (aproximadamente)
REPLACE INTO `empleado` (`idempleado`, `nombre`, `apellido`, `cedula`, `telefono`, `direccion`, `estado`, `idciudad`) VALUES
	(1, 'JORGE ALBERTO', 'GUZMAN MEZA', '4125633', '0991285963', 'CAMPO 9 - CAAGUAZU', 'A', 2),
	(2, 'ROMINA BELEN', 'ARANDA BRITEZ', '4899633', '0982789563', 'EX PASTOREO - CAAGUAZU', 'A', 1);

-- Volcando estructura para tabla taller_bd.forma_pago
CREATE TABLE IF NOT EXISTS `forma_pago` (
  `idforma` int(11) NOT NULL COMMENT 'Codigo de identificacion de la forma de pago',
  `numero_comprobante` varchar(25) NOT NULL COMMENT 'Comprobante de la forma de pago, ya sea transferencia, cheques, efectivo, etc.',
  `monto` double(24,4) NOT NULL COMMENT 'Monto correspondiente de la forma de pago',
  PRIMARY KEY (`idforma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de las formas de pago';

-- Volcando datos para la tabla taller_bd.forma_pago: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.impuesto
CREATE TABLE IF NOT EXISTS `impuesto` (
  `idimpuesto` int(11) NOT NULL COMMENT 'Codigo de identificacion del impuesto',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre del impuesto',
  `valor` double(24,4) NOT NULL COMMENT 'Valor utilizado para el calculo del impuesto',
  PRIMARY KEY (`idimpuesto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de impuestos y sus porcentajes para consumir en los respectivos modulos de faturacion, compra y servicios';

-- Volcando datos para la tabla taller_bd.impuesto: ~2 rows (aproximadamente)
REPLACE INTO `impuesto` (`idimpuesto`, `descripcion`, `valor`) VALUES
	(1, 'EXENTOS', 0.0000),
	(2, 'IVA 5%', 21.0000),
	(3, 'IVA 10%', 11.0000);

-- Volcando estructura para tabla taller_bd.linea
CREATE TABLE IF NOT EXISTS `linea` (
  `idlinea` int(11) NOT NULL COMMENT 'Codigo de identificacion de la linea',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre de la linea',
  PRIMARY KEY (`idlinea`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de las lineas relacionada a los articulos\r\n';

-- Volcando datos para la tabla taller_bd.linea: ~0 rows (aproximadamente)
REPLACE INTO `linea` (`idlinea`, `descripcion`) VALUES
	(1, 'LINEA GENERICA'),
	(2, 'PRUEBA');

-- Volcando estructura para tabla taller_bd.marca
CREATE TABLE IF NOT EXISTS `marca` (
  `idmarca` int(11) NOT NULL COMMENT 'Codigo identificador de la marca',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre de la marca',
  PRIMARY KEY (`idmarca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de las marcas de los articulos';

-- Volcando datos para la tabla taller_bd.marca: ~0 rows (aproximadamente)
REPLACE INTO `marca` (`idmarca`, `descripcion`) VALUES
	(1, 'MARCA GENERICA'),
	(2, 'PRUEBA'),
	(3, 'GABRES');

-- Volcando estructura para tabla taller_bd.motivo
CREATE TABLE IF NOT EXISTS `motivo` (
  `idmotivo` int(11) NOT NULL COMMENT 'Codigo de identificacion del motivo',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre del motivo',
  PRIMARY KEY (`idmotivo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los motivos de las notas de credito y debito del sistema';

-- Volcando datos para la tabla taller_bd.motivo: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.nota_credito_compra
CREATE TABLE IF NOT EXISTS `nota_credito_compra` (
  `idnota` int(11) NOT NULL COMMENT 'Codigo de identificacion de la nota de credito',
  `numero_documento` varchar(24) NOT NULL COMMENT 'Numero del comprobante de la nota de credito',
  `numero_timbrado` int(11) NOT NULL COMMENT 'Numero de timbrado de la nota de credito',
  `fecha` date NOT NULL COMMENT 'Fecha de emision de la nota de credito',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observaciones referentes a la nota de credito',
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identificacion del usuario',
  `idmotivo` int(11) NOT NULL COMMENT 'Codigo de identificacion del motivo relacionado',
  `iddeposito` int(11) NOT NULL COMMENT 'Codigo de identificacion del deposito relacionado',
  PRIMARY KEY (`idnota`),
  KEY `FK_NOTA_CREDITO_COMPRA_USUARIO` (`idusuario`),
  KEY `FK_NOTA_CREDITO_COMPRA_MOTIVO` (`idmotivo`),
  KEY `FK_NOTA_CREDITO_COMPRA_DEPOSITO` (`iddeposito`),
  CONSTRAINT `FK_NOTA_CREDITO_COMPRA_DEPOSITO` FOREIGN KEY (`iddeposito`) REFERENCES `deposito` (`iddeposito`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_NOTA_CREDITO_COMPRA_MOTIVO` FOREIGN KEY (`idmotivo`) REFERENCES `motivo` (`idmotivo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_NOTA_CREDITO_COMPRA_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de las notas de credito del modulo de compras';

-- Volcando datos para la tabla taller_bd.nota_credito_compra: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.nota_credito_compra_compra
CREATE TABLE IF NOT EXISTS `nota_credito_compra_compra` (
  `idnota` int(11) NOT NULL COMMENT 'Codigo de identificacion de la nota de credito',
  `idcompra` int(11) NOT NULL COMMENT 'Codigo de identificacion de la compra',
  PRIMARY KEY (`idnota`,`idcompra`),
  KEY `FK_NOTA_CREDITO_COMPRA_COMPRA_COMPRA` (`idcompra`),
  KEY `FK_NOTA_CREDITO_COMPRA_COMPRA_NOTA_CREDITO_COMPRA` (`idnota`),
  CONSTRAINT `FK_NOTA_CREDITO_COMPRA_COMPRA_COMPRA` FOREIGN KEY (`idcompra`) REFERENCES `compra` (`idcompra`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_NOTA_CREDITO_COMPRA_COMPRA_NOTA_CREDITO_COMPRA` FOREIGN KEY (`idnota`) REFERENCES `nota_credito_compra` (`idnota`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara la relacion entre las notas de credito de compras y las compras';

-- Volcando datos para la tabla taller_bd.nota_credito_compra_compra: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.nota_credito_compra_detalle
CREATE TABLE IF NOT EXISTS `nota_credito_compra_detalle` (
  `idnota` int(11) NOT NULL COMMENT 'Codigo de identificacion de la nota de credito relacionada',
  `idarticulo` int(11) NOT NULL COMMENT 'Codigo de identificacion del articulo relacionado',
  `numero_item` int(11) NOT NULL COMMENT 'Numero de item al momento de ingresar la nota de credito',
  `cantidad` double(24,4) NOT NULL COMMENT 'Cantidad del articulo correspondiente de la nota de credito',
  `costo` double(24,4) NOT NULL COMMENT 'Costo correspondiente del articulo de la nota de credito',
  PRIMARY KEY (`idnota`,`idarticulo`),
  KEY `FK_NOTA_CREDITO_COMPRA_DETALLE_ARTICULO` (`idarticulo`),
  KEY `FK_NOTA_CREDITO_COMPRA_DETALLE_NOTA_CREDITO_COMPRA` (`idnota`),
  CONSTRAINT `FK_NOTA_CREDITO_COMPRA_DETALLE_ARTICULO` FOREIGN KEY (`idarticulo`) REFERENCES `articulo` (`idarticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_NOTA_CREDITO_COMPRA_DETALLE_NOTA_CREDITO_COMPRA` FOREIGN KEY (`idnota`) REFERENCES `nota_credito_compra` (`idnota`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los detalles de los articulos que corresponden a la nota de credito de compras';

-- Volcando datos para la tabla taller_bd.nota_credito_compra_detalle: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.nota_debito_compra
CREATE TABLE IF NOT EXISTS `nota_debito_compra` (
  `idnota` int(11) NOT NULL COMMENT 'Codigo de identificacion de la nota de debito',
  `numero_documento` varchar(24) NOT NULL COMMENT 'Numero del comprobante de la nota de credito',
  `numero_timbrado` int(11) NOT NULL COMMENT 'Numero de timbrado de la nota de credito',
  `fecha` date NOT NULL COMMENT 'Fecha de emision de la nota de credito',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observaciones referentes a la nota de debito',
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identificacion del usuario',
  `idmotivo` int(11) NOT NULL COMMENT 'Codigo de identificacion del motivo relacionado',
  `iddeposito` int(11) NOT NULL COMMENT 'Codigo de identificacion del deposito relacionado',
  PRIMARY KEY (`idnota`) USING BTREE,
  KEY `FK_NOTA_DEBITO_COMPRA_USUARIO` (`idusuario`) USING BTREE,
  KEY `FK_NOTA_DEBITO_COMPRA_MOTIVO` (`idmotivo`) USING BTREE,
  KEY `FK_NOTA_DEBITO_COMPRA_DEPOSITO` (`iddeposito`) USING BTREE,
  CONSTRAINT `FK_NOTA_DEBITO_COMPRA_DEPOSITO` FOREIGN KEY (`iddeposito`) REFERENCES `deposito` (`iddeposito`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_NOTA_DEBITO_COMPRA_MOTIVO` FOREIGN KEY (`idmotivo`) REFERENCES `motivo` (`idmotivo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_NOTA_DEBITO_COMPRA_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de las notas de debito del modulo de compras';

-- Volcando datos para la tabla taller_bd.nota_debito_compra: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.nota_debito_compra_compra
CREATE TABLE IF NOT EXISTS `nota_debito_compra_compra` (
  `idnota` int(11) NOT NULL COMMENT 'Codigo de identificacion de la nota de debito',
  `idcompra` int(11) NOT NULL COMMENT 'Codigo de identificacion de la compra',
  PRIMARY KEY (`idnota`,`idcompra`) USING BTREE,
  KEY `FK_NOTA_DEBITO_COMPRA_COMPRA_COMPRA` (`idcompra`) USING BTREE,
  KEY `FK_NOTA_DEBITO_COMPRA_COMPRA_NOTA_DEBITO_COMPRA` (`idnota`) USING BTREE,
  CONSTRAINT `FK_NOTA_DEBITO_COMPRA_COMPRA` FOREIGN KEY (`idcompra`) REFERENCES `compra` (`idcompra`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_nota_debito_compra_compra_nota_debito_compra` FOREIGN KEY (`idnota`) REFERENCES `nota_debito_compra` (`idnota`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara la relacion entre las notas de debito de compras y las compras';

-- Volcando datos para la tabla taller_bd.nota_debito_compra_compra: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.nota_debito_compra_detalle
CREATE TABLE IF NOT EXISTS `nota_debito_compra_detalle` (
  `idnota` int(11) NOT NULL COMMENT 'Codigo de identificacion de la nota de debito relacionada',
  `idarticulo` int(11) NOT NULL COMMENT 'Codigo de identificacion del articulo relacionado',
  `numero_item` int(11) NOT NULL COMMENT 'Numero de item al momento de ingresar la nota de debito',
  `cantidad` double(24,4) NOT NULL COMMENT 'Cantidad del articulo correspondiente de la nota de debito',
  `costo` double(24,4) NOT NULL COMMENT 'Costo correspondiente del articulo de la nota de debito',
  PRIMARY KEY (`idnota`,`idarticulo`) USING BTREE,
  KEY `FK_NOTA_DEBITO_COMPRA_DETALLE_ARTICULO` (`idarticulo`) USING BTREE,
  KEY `FK_NOTA_DEBITO_COMPRA_DETALLE_NOTA_DEBITO_COMPRA` (`idnota`) USING BTREE,
  CONSTRAINT `FK_NOTA_DEBITO_COMPRA_DETALLE_ARTICULO` FOREIGN KEY (`idarticulo`) REFERENCES `articulo` (`idarticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_nota_debito_compra_detalle_nota_debito_compra` FOREIGN KEY (`idnota`) REFERENCES `nota_debito_compra` (`idnota`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los detalles de los articulos que corresponden a la nota de debito de compras';

-- Volcando datos para la tabla taller_bd.nota_debito_compra_detalle: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.numeracion
CREATE TABLE IF NOT EXISTS `numeracion` (
  `idnumeracion` int(11) NOT NULL COMMENT 'Codigo de identificacion de la numeracion',
  `ult_nro_fac` varchar(25) NOT NULL COMMENT 'Ultimo numero de factura utilizado en ventas ya sea contado o credito',
  `ult_nro_not_cred` varchar(25) NOT NULL COMMENT 'Ultimo numero de nota de credito utilizado.',
  `ult_nro_not_deb` varchar(25) NOT NULL COMMENT 'Ultimo numero de nota de debito utilizado.',
  `ult_nro_ord_tra` varchar(25) NOT NULL COMMENT 'Ultimo numero de orden de trabajo utilizado...',
  PRIMARY KEY (`idnumeracion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los ultimos numeros de facturas, notas de creditos, notas de debitos, etc., que se generaron para poder obtener de nuevo la proxima vez que se necesite';

-- Volcando datos para la tabla taller_bd.numeracion: ~0 rows (aproximadamente)
REPLACE INTO `numeracion` (`idnumeracion`, `ult_nro_fac`, `ult_nro_not_cred`, `ult_nro_not_deb`, `ult_nro_ord_tra`) VALUES
	(1, '001-001-0000006', '001-001-0000000', '001-001-0000000', '000-000-0000102');

-- Volcando estructura para tabla taller_bd.orden_compra
CREATE TABLE IF NOT EXISTS `orden_compra` (
  `idorden` int(11) NOT NULL COMMENT 'Codigo de identificacion de la orden de compra',
  `numero_comprobante` varchar(24) NOT NULL COMMENT 'Numero del comprobante de la orden de compra',
  `fecha` date NOT NULL COMMENT 'Fecha de la orden de compra',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observaciones relacionadas a la orden de compra',
  `estado` varchar(2) NOT NULL COMMENT 'Estado de la orden de compra A=Aprobado, AP=Aprobado Parcial, R=Rechazado, C=Confirmado, P=Pendiente',
  `idempleado` int(11) NOT NULL COMMENT 'Codigo de identificacion del empleado de la order de compra',
  `idproveedor` int(11) NOT NULL COMMENT 'Codigo de identificacion del proveedor',
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identificacion del usuario de la orden de compra',
  PRIMARY KEY (`idorden`),
  KEY `FK_ORDEN_COMPRA_EMPLEADO` (`idempleado`),
  KEY `FK_ORDEN_COMPRA_PROVEEDOR` (`idproveedor`),
  KEY `FK_ORDEN_COMPRA_USUARIO` (`idusuario`),
  CONSTRAINT `FK_ORDEN_COMPRA_EMPLEADO` FOREIGN KEY (`idempleado`) REFERENCES `empleado` (`idempleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDEN_COMPRA_PROVEEDOR` FOREIGN KEY (`idproveedor`) REFERENCES `proveedor` (`idproveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDEN_COMPRA_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a las ordenes de compra del sistema';

-- Volcando datos para la tabla taller_bd.orden_compra: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.orden_compra_detalle
CREATE TABLE IF NOT EXISTS `orden_compra_detalle` (
  `idorden` int(11) NOT NULL COMMENT 'Codigo de identificacion de la order de compra',
  `idarticulo` int(11) NOT NULL COMMENT 'Codigo de identificacion del articulo',
  `numero_item` int(11) NOT NULL COMMENT 'Numero de item o orden en el que se ingreso el articulo en la orden de compra',
  `cantidad` double(24,4) NOT NULL COMMENT 'Cantidad del articulo de la orden de compra',
  `costo` double(24,4) NOT NULL COMMENT 'Costo correspondiente del articulo de la orden de compra',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observacion correspondiente del articulo ingresado en la orden de compra',
  PRIMARY KEY (`idorden`,`idarticulo`),
  KEY `FK_ORDEN_COMPRA_DETALLE_ARTICULO` (`idarticulo`),
  KEY `FK_ORDEN_COMPRA_DETALLE_ORDEN_COMPRA` (`idorden`),
  CONSTRAINT `FK_ORDEN_COMPRA_DETALLE_ARTICULO` FOREIGN KEY (`idarticulo`) REFERENCES `articulo` (`idarticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDEN_COMPRA_DETALLE_ORDEN_COMPRA` FOREIGN KEY (`idorden`) REFERENCES `orden_compra` (`idorden`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de los detalles de las ordenes de compra';

-- Volcando datos para la tabla taller_bd.orden_compra_detalle: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.orden_compra_pedido_compra
CREATE TABLE IF NOT EXISTS `orden_compra_pedido_compra` (
  `idorden` int(11) NOT NULL COMMENT 'Codigo de identificacion de la orden de compra',
  `idpedido` int(11) NOT NULL COMMENT 'Codigo de identificacion del pedido de compra',
  PRIMARY KEY (`idorden`,`idpedido`),
  KEY `FK_ORDEN_COMPRA_PEDIDO_COMPRA_PEDIDO_COMPRA` (`idpedido`),
  KEY `FK_ORDEN_COMPRA_PEDIDO_COMPRA_ORDEN_COMPRA` (`idorden`),
  CONSTRAINT `FK_ORDEN_COMPRA_PEDIDO_COMPRA_ORDEN_COMPRA` FOREIGN KEY (`idorden`) REFERENCES `orden_compra` (`idorden`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDEN_COMPRA_PEDIDO_COMPRA_PEDIDO_COMPRA` FOREIGN KEY (`idpedido`) REFERENCES `pedido_compra` (`idpedido`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de la relacion entre orden de compra y pedido de compra';

-- Volcando datos para la tabla taller_bd.orden_compra_pedido_compra: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.orden_trabajo
CREATE TABLE IF NOT EXISTS `orden_trabajo` (
  `idorden` int(11) NOT NULL COMMENT 'Codigo de identificacion de la orden de servicios',
  `numero_comprobante` varchar(24) NOT NULL COMMENT 'Numero del comprobante de la orden de servicios',
  `fecha` date NOT NULL COMMENT 'Fecha de la orden de servicios',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observaciones relacionadas a la orden de servicios',
  `estado` varchar(2) NOT NULL COMMENT 'Estado de la orden de servicio A=Aprobado, AP=Aprobado Parcial, R=Rechazado, C=Confirmado',
  `idempleado` int(11) NOT NULL COMMENT 'Codigo de identificacion del empleado de la orden de servicio',
  `idcliente` int(11) NOT NULL COMMENT 'Codigo de identificacion del cliente',
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identificacion del usuario de la orden de servicios',
  PRIMARY KEY (`idorden`),
  KEY `FK_ORDEN_SERVICIO_EMPLEADO` (`idempleado`),
  KEY `FK_ORDER_SERVICIO_CLIENTE` (`idcliente`),
  KEY `FK_ORDEN_SERVICIO_USUARIO` (`idusuario`),
  CONSTRAINT `FK_ORDEN_SERVICIO_EMPLEADO` FOREIGN KEY (`idempleado`) REFERENCES `empleado` (`idempleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDEN_SERVICIO_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDER_SERVICIO_CLIENTE` FOREIGN KEY (`idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a las ordenes de trabajo del sistema';

-- Volcando datos para la tabla taller_bd.orden_trabajo: ~2 rows (aproximadamente)
REPLACE INTO `orden_trabajo` (`idorden`, `numero_comprobante`, `fecha`, `observacion`, `estado`, `idempleado`, `idcliente`, `idusuario`) VALUES
	(1, '000-000-0000100', '2023-02-05', 'ORDEN N° 100', 'P', 1, 1, 1),
	(2, '000-000-0000101', '2023-02-05', 'ORDEN N° 101', 'P', 2, 1, 1),
	(3, '000-000-0000102', '2023-02-05', 'ORDEN N° 102', 'C', 1, 1, 1);

-- Volcando estructura para tabla taller_bd.orden_trabajo_detalle
CREATE TABLE IF NOT EXISTS `orden_trabajo_detalle` (
  `idorden` int(11) NOT NULL COMMENT 'Codigo de identificacion de la order de servicio',
  `idarticulo` int(11) NOT NULL COMMENT 'Codigo de identificacion del articulo',
  `numero_item` int(11) NOT NULL COMMENT 'Numero de item o orden en el que se ingreso el articulo en la orden de servicio',
  `cantidad` double(24,4) NOT NULL COMMENT 'Cantidad del articulo de la orden de servicio',
  `precio` double(24,4) NOT NULL COMMENT 'Precio correspondiente del articulo de la orden de servicio',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observacion correspondiente del articulo ingresado en la orden de servicio',
  PRIMARY KEY (`idorden`,`idarticulo`) USING BTREE,
  KEY `FK_ORDEN_SERVICIO_DETALLE_ARTICULO` (`idarticulo`),
  CONSTRAINT `FK_ORDEN_SERVICIO_DETALLE_ARTICULO` FOREIGN KEY (`idarticulo`) REFERENCES `articulo` (`idarticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDEN_SERVICIO_DETALLE_ORDEN_SERVICIO` FOREIGN KEY (`idorden`) REFERENCES `orden_trabajo` (`idorden`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de los detalles de las ordenes de trabajo';

-- Volcando datos para la tabla taller_bd.orden_trabajo_detalle: ~4 rows (aproximadamente)
REPLACE INTO `orden_trabajo_detalle` (`idorden`, `idarticulo`, `numero_item`, `cantidad`, `precio`, `observacion`) VALUES
	(1, 1, 2, 2.0000, 10000.0000, 'CONSUMO DEL CLIENTE'),
	(1, 2, 1, 1.0000, 30000.0000, 'LAVADO EN GENERAL MAS ENGRASE'),
	(2, 1, 1, 3.0000, 10000.0000, 'CONSUMO DEL CLIENTE'),
	(2, 2, 2, 2.0000, 30000.0000, 'LAVADO COMPLETO MAS ASPIRACION'),
	(3, 2, 1, 1.0000, 30000.0000, 'LAVADO COMPLETO');

-- Volcando estructura para tabla taller_bd.pais
CREATE TABLE IF NOT EXISTS `pais` (
  `idpais` int(11) NOT NULL COMMENT 'Codigo de identificacion del pais',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre del pais',
  `nacionalidad` varchar(100) NOT NULL COMMENT 'Nacionalidad perteneciente al pais',
  PRIMARY KEY (`idpais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados de los paises';

-- Volcando datos para la tabla taller_bd.pais: ~0 rows (aproximadamente)
REPLACE INTO `pais` (`idpais`, `descripcion`, `nacionalidad`) VALUES
	(1, 'PARAGUAY', 'PARAGUAYO/A'),
	(2, 'BRASIL', 'BRASIRELO/A');

-- Volcando estructura para tabla taller_bd.pedido_compra
CREATE TABLE IF NOT EXISTS `pedido_compra` (
  `idpedido` int(11) NOT NULL COMMENT 'Codigo de identificacion para el pedido de compra',
  `numero_comprobante` varchar(24) NOT NULL COMMENT 'Numero de comprobante interno del pedido',
  `fecha` date NOT NULL COMMENT 'Fecha del pedido',
  `estado` varchar(2) NOT NULL COMMENT 'Estado del pedido de compra A=Aprobado, AP=Aprobado Parcial, R=Rechazado, C=Confirmado',
  `idempleado` int(11) NOT NULL COMMENT 'Codigo de identificacion del empleado que esta solicitando el pedido',
  `idproveedor` int(11) NOT NULL COMMENT 'Codigo de identificacion del proveedor al que se le realiza el pedido',
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identificacion del usuario',
  PRIMARY KEY (`idpedido`),
  KEY `FK_PEDIDO_COMPRA_EMPLEADO` (`idempleado`),
  KEY `FK_PEDIDO_COMPRA_PROVEEDOR` (`idproveedor`),
  KEY `FK_PEDIDO_COMPRA_USUARIO` (`idusuario`),
  CONSTRAINT `FK_PEDIDO_COMPRA_EMPLEADO` FOREIGN KEY (`idempleado`) REFERENCES `empleado` (`idempleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PEDIDO_COMPRA_PROVEEDOR` FOREIGN KEY (`idproveedor`) REFERENCES `proveedor` (`idproveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PEDIDO_COMPRA_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a las solicitudes de pedidos de compra';

-- Volcando datos para la tabla taller_bd.pedido_compra: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.pedido_compra_detalle
CREATE TABLE IF NOT EXISTS `pedido_compra_detalle` (
  `idpedido` int(11) NOT NULL COMMENT 'Codigo de identificacion del pedido de compra',
  `idarticulo` int(11) NOT NULL COMMENT 'Codigo de identificacion del articulo del pedido',
  `numero_item` int(11) NOT NULL COMMENT 'Numero de orden en la que se registro el articulo en el pedido',
  `cantidad` double(24,4) NOT NULL COMMENT 'Cantidad del articulo solicitado en el pedido',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observacion del porque se solicita el articulo en el pedido',
  PRIMARY KEY (`idpedido`,`idarticulo`),
  KEY `FK_PEDIDO_COMPRA_DETALLE_ARTICULO` (`idarticulo`),
  KEY `FK_PEDIDO_COMPRA_DETALLE_PEDIDO` (`idpedido`),
  CONSTRAINT `FK_PEDIDO_COMPRA_DETALLE_ARTICULO` FOREIGN KEY (`idarticulo`) REFERENCES `articulo` (`idarticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PEDIDO_COMPRA_DETALLE_PEDIDO` FOREIGN KEY (`idpedido`) REFERENCES `pedido_compra` (`idpedido`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los detalles de los articulos solicitados en el pedido de compra';

-- Volcando datos para la tabla taller_bd.pedido_compra_detalle: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.proveedor
CREATE TABLE IF NOT EXISTS `proveedor` (
  `idproveedor` int(11) NOT NULL COMMENT 'Codigo de identificacion del proveedor',
  `razon_social` varchar(100) NOT NULL COMMENT 'Razon social o nombre del proveedor',
  `ruc` varchar(25) NOT NULL COMMENT 'Registro unico del contribuyente del proveedor',
  `representante_legal` varchar(100) DEFAULT NULL COMMENT 'Representante legal de la empresa',
  `telefono` varchar(15) DEFAULT NULL COMMENT 'Telefono del proveedor',
  `direccion` varchar(250) DEFAULT NULL COMMENT 'Direccion del proveedor',
  `estado` varchar(1) NOT NULL DEFAULT 'A' COMMENT 'Estado del proveedor, A=Activo y I=Inactivo',
  `idciudad` int(11) NOT NULL COMMENT 'Codigo de identificacion de la ciudad relacionada',
  PRIMARY KEY (`idproveedor`),
  UNIQUE KEY `ruc` (`ruc`),
  KEY `FK_PROVEEDOR_CIUDAD` (`idciudad`),
  CONSTRAINT `FK_PROVEEDOR_CIUDAD` FOREIGN KEY (`idciudad`) REFERENCES `ciudad` (`idciudad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los proveedores\r\n';

-- Volcando datos para la tabla taller_bd.proveedor: ~1 rows (aproximadamente)
REPLACE INTO `proveedor` (`idproveedor`, `razon_social`, `ruc`, `representante_legal`, `telefono`, `direccion`, `estado`, `idciudad`) VALUES
	(1, 'ARANDU SYSTEMS', '5955455-0', 'ARMANDO ARIEL PERALTA MARTINEZ', '0975489075', 'EX CAMPO 9 - CAAGUAZU', 'A', 1);

-- Volcando estructura para tabla taller_bd.servicio
CREATE TABLE IF NOT EXISTS `servicio` (
  `idservicio` int(11) NOT NULL COMMENT 'Codigo de identificacion del servicio',
  `numero_documento` varchar(24) NOT NULL COMMENT 'Numero de comprobante del servicio',
  `numero_timbrado` int(11) NOT NULL COMMENT 'Numero del timbrado del servicio',
  `fecha` date NOT NULL COMMENT 'Fecha del documento del servicio ingresado',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observaciones referentes al servicio',
  `idcliente` int(11) NOT NULL COMMENT 'Codigo de identificacion del cliente del servicio',
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identificacion del usuario que ingreso el servicio',
  `iddeposito` int(11) NOT NULL COMMENT 'Codigo de identificacion del deposito en el cual se moveran los gastos de los articulos utilizados en el servicio',
  PRIMARY KEY (`idservicio`),
  KEY `FK_SERVICIO_CLIENTE` (`idcliente`),
  KEY `FK_SERVICIO_USUARIO` (`idusuario`),
  KEY `FK_SERVICIO_DEPOSITO` (`iddeposito`),
  CONSTRAINT `FK_SERVICIO_CLIENTE` FOREIGN KEY (`idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_SERVICIO_DEPOSITO` FOREIGN KEY (`iddeposito`) REFERENCES `deposito` (`iddeposito`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_SERVICIO_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos principales del servicio\r\n';

-- Volcando datos para la tabla taller_bd.servicio: ~2 rows (aproximadamente)
REPLACE INTO `servicio` (`idservicio`, `numero_documento`, `numero_timbrado`, `fecha`, `observacion`, `idcliente`, `idusuario`, `iddeposito`) VALUES
	(1, '001-001-0000001', 11112222, '2023-02-05', 'FACTURACION DE SERVICIO', 1, 1, 1),
	(2, '001-001-0000002', 11112222, '2023-02-05', 'PRUEBA', 1, 1, 1);

-- Volcando estructura para tabla taller_bd.servicio_apertura
CREATE TABLE IF NOT EXISTS `servicio_apertura` (
  `idservicio` int(11) NOT NULL COMMENT 'Codigo de identificacion del servicio',
  `idapertura` int(11) NOT NULL COMMENT 'Codigo de identificacion de la apertura',
  PRIMARY KEY (`idservicio`,`idapertura`),
  KEY `FK_SERVICIO_APERTURA_APERTURA` (`idapertura`),
  CONSTRAINT `FK_SERVICIO_APERTURA_APERTURA` FOREIGN KEY (`idapertura`) REFERENCES `apertura` (`idapertura`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_SERVICIO_APERTURA_SERVICIO` FOREIGN KEY (`idservicio`) REFERENCES `servicio` (`idservicio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados entre servicios y la apertura de caja';

-- Volcando datos para la tabla taller_bd.servicio_apertura: ~2 rows (aproximadamente)
REPLACE INTO `servicio_apertura` (`idservicio`, `idapertura`) VALUES
	(1, 1),
	(2, 1);

-- Volcando estructura para tabla taller_bd.servicio_detalle
CREATE TABLE IF NOT EXISTS `servicio_detalle` (
  `idservicio` int(11) NOT NULL COMMENT 'Codigo de identificacion del servicio relacionado',
  `idarticulo` int(11) NOT NULL COMMENT 'Codigo de identificacion del articulo relacionado',
  `numero_item` int(11) NOT NULL COMMENT 'Numero de item al momento de ingresar el servicio',
  `cantidad` double(24,4) NOT NULL COMMENT 'Cantidad del articulo correspondiente al servicio',
  `precio` double(24,4) NOT NULL COMMENT 'Precio correspondiente del articulo del servicio',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observaciones sobre el articulo utilizado en el movimiento',
  PRIMARY KEY (`idservicio`,`idarticulo`),
  KEY `FK_SERVICIO_DETALLE_ARTICULO` (`idarticulo`),
  KEY `FK_SERVICIO_DETALLE_SERVICIO` (`idservicio`),
  CONSTRAINT `FK_SERVICIO_DETALLE_ARTICULO` FOREIGN KEY (`idarticulo`) REFERENCES `articulo` (`idarticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_SERVICIO_DETALLE_SERVICIO` FOREIGN KEY (`idservicio`) REFERENCES `servicio` (`idservicio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los detalles de los articulos que corresponden al servicio\r\n';

-- Volcando datos para la tabla taller_bd.servicio_detalle: ~2 rows (aproximadamente)
REPLACE INTO `servicio_detalle` (`idservicio`, `idarticulo`, `numero_item`, `cantidad`, `precio`, `observacion`) VALUES
	(1, 1, 2, 2.0000, 10000.0000, 'CONSUMO DEL CLIENTE'),
	(1, 2, 1, 1.0000, 30000.0000, 'LAVADO EN GENERAL MAS ENGRASE'),
	(2, 2, 1, 1.0000, 30000.0000, 'LAVADO COMPLETO');

-- Volcando estructura para tabla taller_bd.servicio_forma_pago
CREATE TABLE IF NOT EXISTS `servicio_forma_pago` (
  `idservicio` int(11) NOT NULL COMMENT 'Codigo de identificacion del servicio',
  `idforma` int(11) NOT NULL COMMENT 'Codigo de identificacion de la forma de pago',
  PRIMARY KEY (`idservicio`,`idforma`),
  KEY `FK_SERVICIO_FORMA_PAGO_FORMA_PAGO` (`idforma`),
  KEY `FK_SERVICIO_FORMA_PAGO_SERVICIO` (`idservicio`),
  CONSTRAINT `FK_SERVICIO_FORMA_PAGO_FORMA_PAGO` FOREIGN KEY (`idforma`) REFERENCES `forma_pago` (`idforma`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_SERVICIO_FORMA_PAGO_SERVICIO` FOREIGN KEY (`idservicio`) REFERENCES `servicio` (`idservicio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados entre ventas y formas de pago';

-- Volcando datos para la tabla taller_bd.servicio_forma_pago: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.servicio_orden_trabajo
CREATE TABLE IF NOT EXISTS `servicio_orden_trabajo` (
  `idservicio` int(11) NOT NULL COMMENT 'Codigo de identificacion del servicio',
  `idorden` int(11) NOT NULL COMMENT 'Codigo de identificacion de la orden de trabajo',
  PRIMARY KEY (`idservicio`,`idorden`),
  KEY `FK_SERVICIO_ORDEN_TRABAJO_ORDEN_TRABAJO` (`idorden`),
  CONSTRAINT `FK_SERVICIO_ORDEN_TRABAJO_ORDEN_TRABAJO` FOREIGN KEY (`idorden`) REFERENCES `orden_trabajo` (`idorden`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_SERVICIO_ORDEN_TRABAJO_SERVICIO` FOREIGN KEY (`idservicio`) REFERENCES `servicio` (`idservicio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados entre servicios y ordenes de trabajo';

-- Volcando datos para la tabla taller_bd.servicio_orden_trabajo: ~0 rows (aproximadamente)
REPLACE INTO `servicio_orden_trabajo` (`idservicio`, `idorden`) VALUES
	(2, 3);

-- Volcando estructura para tabla taller_bd.solicitud_servicio
CREATE TABLE IF NOT EXISTS `solicitud_servicio` (
  `idsolicitud` int(11) NOT NULL,
  `numero_documento` varchar(25) NOT NULL,
  `fecha` date NOT NULL,
  `observacion` varchar(250) NOT NULL,
  `idempleado` int(11) NOT NULL,
  `idcliente` int(11) NOT NULL,
  `idusuario` int(11) NOT NULL,
  PRIMARY KEY (`idsolicitud`),
  KEY `FK_ORDEN_TRABAJO_EMPLEADO` (`idempleado`),
  KEY `FK_ORDEN_TRABAJO_CLIENTE` (`idcliente`),
  KEY `FK_ORDEN_TRABAJO_USUARIO` (`idusuario`),
  CONSTRAINT `FK_ORDEN_TRABAJO_CLIENTE` FOREIGN KEY (`idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDEN_TRABAJO_EMPLEADO` FOREIGN KEY (`idempleado`) REFERENCES `empleado` (`idempleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDEN_TRABAJO_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de las solicitudes de servicios de los clientes';

-- Volcando datos para la tabla taller_bd.solicitud_servicio: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.solicitud_servicio_detalle
CREATE TABLE IF NOT EXISTS `solicitud_servicio_detalle` (
  `idservicio` int(11) NOT NULL,
  `idarticulo` int(11) NOT NULL,
  `numero_item` int(11) NOT NULL,
  `precio` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `observacion` int(11) NOT NULL,
  PRIMARY KEY (`idservicio`,`idarticulo`,`numero_item`),
  KEY `FK_SOLICITUD_SERVICIO_DETALLE_ARTICULO` (`idarticulo`),
  CONSTRAINT `FK_SOLICITUD_SERVICIO_DETALLE_ARTICULO` FOREIGN KEY (`idarticulo`) REFERENCES `articulo` (`idarticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_SOLICITUD_SERVICIO_DETALLE_SOLICITUD_SERVICIO` FOREIGN KEY (`idservicio`) REFERENCES `solicitud_servicio` (`idsolicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de los detalles de la solicitud de servicios';

-- Volcando datos para la tabla taller_bd.solicitud_servicio_detalle: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.timbrado
CREATE TABLE IF NOT EXISTS `timbrado` (
  `idtimbrado` int(11) NOT NULL COMMENT 'Codigo de identificacion del timbrado',
  `timbrado` int(11) NOT NULL COMMENT 'Numero de timbrado el cual se va a utilizar',
  `fecha_inicial` date NOT NULL COMMENT 'Fecha correspondiente a la vigencia del timbrado',
  `fecha_final` date NOT NULL COMMENT 'Fecha correspondiente del vencimiento del timbrado',
  `numero_desde` int(11) NOT NULL COMMENT 'Numero inicial donde empieza la numeracion de la factura',
  `numero_hasta` int(11) NOT NULL COMMENT 'Numero final donde termina la numeracion de la factura',
  `establecimiento` int(11) NOT NULL COMMENT 'El establecimiento de asiganado a la factura',
  `punto_emision` int(11) NOT NULL COMMENT 'El punto de emision de la factura',
  `idtipo` int(11) NOT NULL COMMENT 'Codigo de identificacion del tipo de documento',
  PRIMARY KEY (`idtimbrado`),
  KEY `FK_TIMBRADO_TIPO_COMPROBANTE` (`idtipo`),
  CONSTRAINT `FK_TIMBRADO_TIPO_COMPROBANTE` FOREIGN KEY (`idtipo`) REFERENCES `tipo_documento` (`idtipo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los timbrados de las facturaciones de venta y servicios';

-- Volcando datos para la tabla taller_bd.timbrado: ~4 rows (aproximadamente)
REPLACE INTO `timbrado` (`idtimbrado`, `timbrado`, `fecha_inicial`, `fecha_final`, `numero_desde`, `numero_hasta`, `establecimiento`, `punto_emision`, `idtipo`) VALUES
	(1, 11112222, '2023-01-01', '2023-02-01', 1, 100, 1, 1, 1),
	(2, 22223333, '2023-01-01', '2023-01-31', 1, 100, 1, 1, 2),
	(3, 33334444, '2023-01-01', '2023-02-01', 1, 100, 1, 1, 3);

-- Volcando estructura para tabla taller_bd.tipo_articulo
CREATE TABLE IF NOT EXISTS `tipo_articulo` (
  `idtipo` int(11) NOT NULL COMMENT 'Codigo de identificacion del tipo de articulo',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre del tipo de articulo',
  PRIMARY KEY (`idtipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados al tipo de articulo de los articulos';

-- Volcando datos para la tabla taller_bd.tipo_articulo: ~2 rows (aproximadamente)
REPLACE INTO `tipo_articulo` (`idtipo`, `descripcion`) VALUES
	(1, 'ADQUIRIDOS'),
	(2, 'SERVICIOS');

-- Volcando estructura para tabla taller_bd.tipo_documento
CREATE TABLE IF NOT EXISTS `tipo_documento` (
  `idtipo` int(11) NOT NULL COMMENT 'Codigo de identificacion del  tipo de documento',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion del tipo de documento',
  PRIMARY KEY (`idtipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de los tipos de documentos del sistema, ya sea factura, nota de credito, nota de debito, remisiones, traslados, etc.';

-- Volcando datos para la tabla taller_bd.tipo_documento: ~4 rows (aproximadamente)
REPLACE INTO `tipo_documento` (`idtipo`, `descripcion`) VALUES
	(1, 'FACTURA'),
	(2, 'NOTA DE CREDITO'),
	(3, 'NOTA DE DEBITO');

-- Volcando estructura para tabla taller_bd.tipo_forma_pago
CREATE TABLE IF NOT EXISTS `tipo_forma_pago` (
  `idtipo` int(11) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`idtipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de los tipos de formas de pago';

-- Volcando datos para la tabla taller_bd.tipo_forma_pago: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.unidad_medida
CREATE TABLE IF NOT EXISTS `unidad_medida` (
  `idunidad` int(11) NOT NULL COMMENT 'Codigo de identificacion de la unidad de medida',
  `descripcion` varchar(100) NOT NULL COMMENT 'Descripcion o nombre de la unidad de medida',
  `abreviacion` varchar(5) NOT NULL COMMENT 'Abreviacion o simbolo de la unidad de medida',
  PRIMARY KEY (`idunidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de la unidad de medida de los articulos';

-- Volcando datos para la tabla taller_bd.unidad_medida: ~2 rows (aproximadamente)
REPLACE INTO `unidad_medida` (`idunidad`, `descripcion`, `abreviacion`) VALUES
	(1, 'UNIDAD', 'UN'),
	(2, 'KILOGRAMO', 'KG'),
	(3, 'LITRO', 'LT');

-- Volcando estructura para tabla taller_bd.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identificacion del usuario',
  `nombre` varchar(100) NOT NULL COMMENT 'Nombre del usuario',
  `apellido` varchar(100) NOT NULL COMMENT 'Apellido del usuario',
  `telefono` varchar(15) DEFAULT NULL COMMENT 'Telefono del usuario',
  `cedula` varchar(25) DEFAULT NULL COMMENT 'Cedula de identidad del usuario',
  `direccion` varchar(250) DEFAULT NULL COMMENT 'Direccion de domicilio del usuario',
  `correo` varchar(200) NOT NULL COMMENT 'Correo del usuario',
  `clave` varchar(256) NOT NULL COMMENT 'Clave del usuario cifrado',
  PRIMARY KEY (`idusuario`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los usuarios del sistema';

-- Volcando datos para la tabla taller_bd.usuario: ~1 rows (aproximadamente)
REPLACE INTO `usuario` (`idusuario`, `nombre`, `apellido`, `telefono`, `cedula`, `direccion`, `correo`, `clave`) VALUES
	(1, 'ADMIN', 'ADMIN', '0000 000 000', '0000000', 'SIN DIRECCION', 'admin@gmail.com', '21232f297a57a5a743894a0e4a801fc3');

-- Volcando estructura para tabla taller_bd.venta
CREATE TABLE IF NOT EXISTS `venta` (
  `idventa` int(11) NOT NULL COMMENT 'Codigo de identificacion de la venta',
  `numero_documento` varchar(24) NOT NULL COMMENT 'Numero de comprobante de la venta',
  `numero_timbrado` int(11) NOT NULL COMMENT 'Numero del timbrado de la venta',
  `fecha` date NOT NULL COMMENT 'Fecha del documento de venta ingresado',
  `observacion` varchar(250) DEFAULT NULL COMMENT 'Observaciones referentes a la venta',
  `idcliente` int(11) NOT NULL COMMENT 'Codigo de identificacion del cliente de la venta',
  `iddeposito` int(11) NOT NULL COMMENT 'Codigo de identificacion del deposito de la venta ',
  `idusuario` int(11) NOT NULL COMMENT 'Codigo de identificacion del usuario que ingreso la venta',
  PRIMARY KEY (`idventa`),
  KEY `FK_VENTA_CLIENTE` (`idcliente`),
  KEY `FK_VENTA_DEPOSITO` (`iddeposito`),
  KEY `FK_VENTA_USUARIO` (`idusuario`),
  CONSTRAINT `FK_VENTA_CLIENTE` FOREIGN KEY (`idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_VENTA_DEPOSITO` FOREIGN KEY (`iddeposito`) REFERENCES `deposito` (`iddeposito`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_VENTA_USUARIO` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos principales de la venta\r\n';

-- Volcando datos para la tabla taller_bd.venta: ~4 rows (aproximadamente)
REPLACE INTO `venta` (`idventa`, `numero_documento`, `numero_timbrado`, `fecha`, `observacion`, `idcliente`, `iddeposito`, `idusuario`) VALUES
	(1, '001-001-0000003', 11112222, '2023-02-06', '', 1, 1, 1),
	(2, '001-001-0000004', 11112222, '2023-07-10', '', 1, 1, 1),
	(3, '001-001-0000005', 11112222, '2023-09-06', 'ASDASDA', 1, 1, 1),
	(4, '001-001-0000006', 11112222, '2023-09-06', '', 1, 1, 1);

-- Volcando estructura para tabla taller_bd.venta_apertura
CREATE TABLE IF NOT EXISTS `venta_apertura` (
  `idventa` int(11) NOT NULL COMMENT 'Codigo de identificacion de la venta relacionada',
  `idapertura` int(11) NOT NULL COMMENT 'Codigo de identificacion de la apertura relacionada',
  PRIMARY KEY (`idventa`,`idapertura`),
  KEY `FK_VENTA_APERTURA_APERTURA` (`idapertura`),
  KEY `FK_VENTA_APERTURA_VENTA` (`idventa`),
  CONSTRAINT `FK_VENTA_APERTURA_APERTURA` FOREIGN KEY (`idapertura`) REFERENCES `apertura` (`idapertura`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_VENTA_APERTURA_VENTA` FOREIGN KEY (`idventa`) REFERENCES `venta` (`idventa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados entre la venta y la apertura de la caja';

-- Volcando datos para la tabla taller_bd.venta_apertura: ~4 rows (aproximadamente)
REPLACE INTO `venta_apertura` (`idventa`, `idapertura`) VALUES
	(1, 1),
	(2, 1),
	(3, 1),
	(4, 1);

-- Volcando estructura para tabla taller_bd.venta_cuota
CREATE TABLE IF NOT EXISTS `venta_cuota` (
  `idventa` int(11) NOT NULL COMMENT 'Codigo de identificacion de la venta',
  `numero` int(11) NOT NULL COMMENT 'Numero de la cuota',
  `fecha` date NOT NULL COMMENT 'Fecha de vencimiento de la cuota',
  `monto` double(24,4) NOT NULL COMMENT 'monto correspondiente de la cuota',
  `saldo` double(24,4) NOT NULL COMMENT 'Saldo disponible de la cuota',
  PRIMARY KEY (`idventa`,`numero`,`fecha`) USING BTREE,
  KEY `FK_VENTA_CUOTA_VENTA` (`idventa`),
  CONSTRAINT `FK_VENTA_CUOTA_VENTA` FOREIGN KEY (`idventa`) REFERENCES `venta` (`idventa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos de las cuotas de las ventas a creditos realizadas';

-- Volcando datos para la tabla taller_bd.venta_cuota: ~0 rows (aproximadamente)

-- Volcando estructura para tabla taller_bd.venta_detalle
CREATE TABLE IF NOT EXISTS `venta_detalle` (
  `idventa` int(11) NOT NULL COMMENT 'Codigo de identificacion de la venta relacionada',
  `idarticulo` int(11) NOT NULL COMMENT 'Codigo de identificacion del articulo relacionado',
  `numero_item` int(11) NOT NULL COMMENT 'Numero de item al momento de ingresar la venta',
  `cantidad` double(24,4) NOT NULL COMMENT 'Cantidad del articulo correspondiente de la venta',
  `precio` double(24,4) NOT NULL COMMENT 'Precio correspondiente del articulo de venta',
  PRIMARY KEY (`idventa`,`idarticulo`),
  KEY `FK_VENTA_DETALLE_ARTICULO` (`idarticulo`),
  KEY `FK_VENTA_DETALLE_VENTA` (`idventa`),
  CONSTRAINT `FK_VENTA_DETALLE_ARTICULO` FOREIGN KEY (`idarticulo`) REFERENCES `articulo` (`idarticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_VENTA_DETALLE_VENTA` FOREIGN KEY (`idventa`) REFERENCES `venta` (`idventa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados a los detalles de los articulos que corresponden a la venta\r\n';

-- Volcando datos para la tabla taller_bd.venta_detalle: ~7 rows (aproximadamente)
REPLACE INTO `venta_detalle` (`idventa`, `idarticulo`, `numero_item`, `cantidad`, `precio`) VALUES
	(1, 1, 2, 20.0000, 10000.0000),
	(1, 2, 1, 5.0000, 30000.0000),
	(2, 2, 1, 6.0000, 30000.0000),
	(2, 3, 2, 10.0000, 9000.0000),
	(3, 1, 1, 10.0000, 10000.0000),
	(4, 1, 1, 2.0000, 10000.0000),
	(4, 4, 2, 1.0000, 20000.0000);

-- Volcando estructura para tabla taller_bd.venta_forma_pago
CREATE TABLE IF NOT EXISTS `venta_forma_pago` (
  `idventa` int(11) NOT NULL COMMENT 'Codigo de identificacion de la venta',
  `idforma` int(11) NOT NULL COMMENT 'Codigo de identificacion de la forma de pago',
  PRIMARY KEY (`idventa`,`idforma`),
  KEY `FK_VENTA_FORMA_PAGO_FORMA_PAGO` (`idforma`),
  KEY `FK_VENTA_FORMA_PAGO_VENTA` (`idventa`),
  CONSTRAINT `FK_VENTA_FORMA_PAGO_FORMA_PAGO` FOREIGN KEY (`idforma`) REFERENCES `forma_pago` (`idforma`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_VENTA_FORMA_PAGO_VENTA` FOREIGN KEY (`idventa`) REFERENCES `venta` (`idventa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Entidad que almacenara los datos relacionados entre ventas y formas de pago';

-- Volcando datos para la tabla taller_bd.venta_forma_pago: ~0 rows (aproximadamente)

-- Volcando estructura para vista taller_bd.apertura_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `apertura_v` (
	`APERTURA_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la apertura',
	`CAJA_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la caja relacionadad',
	`CAJA_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre de la caja' COLLATE 'utf8mb4_general_ci',
	`APERTURA_FECHA` VARCHAR(22) NULL COLLATE 'utf8mb4_general_ci',
	`APERTURA_MONTO_INICIAL` DOUBLE(24,4) NOT NULL COMMENT 'Monto inicial con la cual se realiza la apertura de caja',
	`APERTURA_OBSERVACION` VARCHAR(250) NULL COMMENT 'Observaciones relacionadas a la apertura' COLLATE 'utf8mb4_general_ci',
	`APERTURA_ESTADO` VARCHAR(10) NOT NULL COLLATE 'utf8mb4_general_ci',
	`USUARIO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identifiacacion del usuario de la apertura',
	`USUARIO_DESCRIPCION` VARCHAR(201) NOT NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.articulo_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `articulo_v` (
	`ARTICULO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de indentificacion del articulo',
	`ARTICULO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre del articulo' COLLATE 'utf8mb4_general_ci',
	`ARTICULO_REFERENCIA` VARCHAR(100) NULL COMMENT 'Referencia o algun datos que el articulo tenga visible' COLLATE 'utf8mb4_general_ci',
	`ARTICULO_ESTADO` VARCHAR(10) NOT NULL COLLATE 'utf8mb4_general_ci',
	`MARCA_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la marca relacionada',
	`MARCA_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre de la marca' COLLATE 'utf8mb4_general_ci',
	`LINEA_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la linea relacionada',
	`LINEA_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre de la linea' COLLATE 'utf8mb4_general_ci',
	`UNIDAD_MEDIDA_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la unidad de medida relacionada',
	`UNIDAD_MEDIDA_SIMBOLO` VARCHAR(5) NOT NULL COMMENT 'Abreviacion o simbolo de la unidad de medida' COLLATE 'utf8mb4_general_ci',
	`TIPO_ARTICULO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del tipo de articulo relacionado',
	`TIPO_ARTICULO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre del tipo de articulo' COLLATE 'utf8mb4_general_ci',
	`ARTICULO_COSTO` DOUBLE(24,4) NOT NULL,
	`ARTICULO_PRECIO` DOUBLE(24,4) NOT NULL,
	`IMPUESTO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del impuesto del articulo',
	`IMPUESTO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre del impuesto' COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.ciudad_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `ciudad_v` (
	`CIUDAD_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la ciudad',
	`CIUDAD_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre de la ciudad' COLLATE 'utf8mb4_general_ci',
	`PAIS_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del pais relacionado a la ciudad',
	`PAIS_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre del pais' COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.cliente_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `cliente_v` (
	`CLIENTE_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del cliente',
	`CLIENTE_NOMBRE` VARCHAR(201) NOT NULL COLLATE 'utf8mb4_general_ci',
	`CLIENTE_CEDULA` VARCHAR(25) NOT NULL COMMENT 'Cedula de identidad del cliente' COLLATE 'utf8mb4_general_ci',
	`CLIENTE_TELEFONO` VARCHAR(15) NULL COMMENT 'Telefono del cliente' COLLATE 'utf8mb4_general_ci',
	`CLIENTE_DIRECCION` VARCHAR(250) NULL COMMENT 'Direccion de domicilio del cliente' COLLATE 'utf8mb4_general_ci',
	`CLIENTE_ESTADO` VARCHAR(1) NOT NULL COMMENT 'Estado del cliente, A=Activo y I=Inactivo' COLLATE 'utf8mb4_general_ci',
	`CIUDAD_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la ciudad relacionadad',
	`CIUDAD_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre de la ciudad' COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.compra_detalle_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `compra_detalle_v` (
	`COMPRA_DETALLE_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la compra relacionada',
	`COMPRA_NUMERO_DOCUMENTO` VARCHAR(24) NOT NULL COMMENT 'Numero de documento o comprobante de compra' COLLATE 'utf8mb4_general_ci',
	`COMPRA_NUMERO_TIMBRADO` INT(11) NOT NULL COMMENT 'Numero de timbrado del documento de compra',
	`COMPRA_FECHA` VARCHAR(10) NULL COLLATE 'utf8mb4_general_ci',
	`COMPRA_DETALLE_ARTICULO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del articulo relacionado',
	`ARTICULO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre del articulo' COLLATE 'utf8mb4_general_ci',
	`COMPRA_DETALLE_NUMERO_ITEM` INT(11) NOT NULL COMMENT 'Numero de item al momento de ingresar la compra',
	`COMPRA_DETALLE_CANTIDAD` DOUBLE(24,4) NOT NULL COMMENT 'Cantidad del articulo correspondiente de la compra',
	`COMPRA_DETALLE_PRECIO` DOUBLE(24,4) NOT NULL COMMENT 'Costo correspondiente del articulo de compra'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.compra_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `compra_v` (
	`COMPRA_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la compra',
	`COMPRA_NUMERO_DOCUMENTO` VARCHAR(24) NOT NULL COMMENT 'Numero de documento o comprobante de compra' COLLATE 'utf8mb4_general_ci',
	`COMPRA_NUMERO_TIMBRADO` INT(11) NOT NULL COMMENT 'Numero de timbrado del documento de compra',
	`PROVEEDOR_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del proveedor de la compra',
	`PROVEEDOR_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Razon social o nombre del proveedor' COLLATE 'utf8mb4_general_ci',
	`COMPRA_FECHA` VARCHAR(10) NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.configuracion_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `configuracion_v` (
	`CONFIGURACION_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la configuracion',
	`TIPO_ARTICULO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion para el tipo de articulo servicios, para que no mueva existencias',
	`TIPO_SERVICIO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre del tipo de articulo' COLLATE 'utf8mb4_general_ci',
	`CANTIDAD_MAXIMA_FACTURACION` INT(11) NOT NULL COMMENT 'Cantidad maxima de items que pueden ser facturados por operacion',
	`FACTURACION_TIMBRADO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del timbrado asignado para facturaciones y servicios',
	`FT_TIPO_DOCUMENTO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion del tipo de documento' COLLATE 'utf8mb4_general_ci',
	`NOTA_CREDITO_TIMBRADO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del timbrado asignado para Notas de credito',
	`NC_TIPO_DOCUMENTO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion del tipo de documento' COLLATE 'utf8mb4_general_ci',
	`NOTA_DEBITO_TIMBRADO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del timbrado asignado para Notas de debito',
	`ND_TIPO_DOCUMENTO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion del tipo de documento' COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.empleado_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `empleado_v` (
	`EMPL_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del empleado',
	`EMPL_DESCRIPCION` VARCHAR(201) NOT NULL COLLATE 'utf8mb4_general_ci',
	`EMPL_CI` VARCHAR(25) NOT NULL COMMENT 'Cedula de identidad del empleado' COLLATE 'utf8mb4_general_ci',
	`EMPL_TEL` VARCHAR(25) NULL COMMENT 'Telefono o celular del empleado' COLLATE 'utf8mb4_general_ci',
	`EMPL_DIRECCION` VARCHAR(250) NULL COMMENT 'Direccion de domicilio del empleado' COLLATE 'utf8mb4_general_ci',
	`EMPL_ESTADO` VARCHAR(1) NOT NULL COMMENT 'Estado del empleado, A=Activo y I=Inactivo' COLLATE 'utf8mb4_general_ci',
	`EMPL_COI_COD` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la ciudad',
	`EMPL_CIUDAD` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre de la ciudad' COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.orden_trabajo_detalle_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `orden_trabajo_detalle_v` (
	`ORDEN_DETALLE_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la order de servicio',
	`ORDEN_NUMERO_DOCUMENTO` VARCHAR(24) NOT NULL COMMENT 'Numero del comprobante de la orden de servicios' COLLATE 'utf8mb4_general_ci',
	`ORDEN_FECHA` VARCHAR(10) NULL COLLATE 'utf8mb4_general_ci',
	`ORDEN_DETALLE_ARTICULO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del articulo',
	`ARTICULO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre del articulo' COLLATE 'utf8mb4_general_ci',
	`ORDEN_DETALLE_NUMERO_ITEM` INT(11) NOT NULL COMMENT 'Numero de item o orden en el que se ingreso el articulo en la orden de servicio',
	`ORDEN_DETALLE_CANTIDAD` DOUBLE(24,4) NOT NULL COMMENT 'Cantidad del articulo de la orden de servicio',
	`ORDEN_DETALLE_PRECIO` DOUBLE(24,4) NOT NULL COMMENT 'Precio correspondiente del articulo de la orden de servicio',
	`ORDEN_OBSERVACION` VARCHAR(250) NULL COMMENT 'Observacion correspondiente del articulo ingresado en la orden de servicio' COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.orden_trabajo_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `orden_trabajo_v` (
	`ORDEN_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la orden de servicios',
	`ORDEN_COMPROBANTE` VARCHAR(24) NOT NULL COMMENT 'Numero del comprobante de la orden de servicios' COLLATE 'utf8mb4_general_ci',
	`ORDEN_FECHA` VARCHAR(10) NULL COLLATE 'utf8mb4_general_ci',
	`ORDEN_OBSERVACION` VARCHAR(250) NULL COMMENT 'Observaciones relacionadas a la orden de servicios' COLLATE 'utf8mb4_general_ci',
	`ORDEN_ESTADO` VARCHAR(16) NOT NULL COLLATE 'utf8mb4_general_ci',
	`ORDEN_EMPLEADO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del empleado de la orden de servicio',
	`EMPLEADO_DESCRIPCION` VARCHAR(201) NOT NULL COLLATE 'utf8mb4_general_ci',
	`ORDEN_CLIENTE` INT(11) NOT NULL COMMENT 'Codigo de identificacion del cliente',
	`CLIENTE_DESCRIPCION` VARCHAR(201) NOT NULL COLLATE 'utf8mb4_general_ci',
	`ORDEN_USUARIO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del usuario de la orden de servicios',
	`USUARIO_DESCRIPCION` VARCHAR(201) NOT NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.proveedor_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `proveedor_v` (
	`PROVEEDOR_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del proveedor',
	`PROVEEDOR_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Razon social o nombre del proveedor' COLLATE 'utf8mb4_general_ci',
	`PROVEEDOR_RUC` VARCHAR(25) NOT NULL COMMENT 'Registro unico del contribuyente del proveedor' COLLATE 'utf8mb4_general_ci',
	`PROVEEDOR_REPRESENTANTE` VARCHAR(100) NULL COMMENT 'Representante legal de la empresa' COLLATE 'utf8mb4_general_ci',
	`PROVEEDOR_TELEFONO` VARCHAR(15) NULL COMMENT 'Telefono del proveedor' COLLATE 'utf8mb4_general_ci',
	`PROVEEDOR_DIRECCION` VARCHAR(250) NULL COMMENT 'Direccion del proveedor' COLLATE 'utf8mb4_general_ci',
	`PROVEEDOR_ESTADO` VARCHAR(1) NOT NULL COMMENT 'Estado del proveedor, A=Activo y I=Inactivo' COLLATE 'utf8mb4_general_ci',
	`CIUDAD_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la ciudad relacionada',
	`CIUDAD_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre de la ciudad' COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.servicio_detalle_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `servicio_detalle_v` (
	`SERVICIO_DETALLE_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del servicio relacionado',
	`ARTICULO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del articulo relacionado',
	`ARTICULO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre del articulo' COLLATE 'utf8mb4_general_ci',
	`SERVICIO_DETALLE_ITEM` INT(11) NOT NULL COMMENT 'Numero de item al momento de ingresar el servicio',
	`SERVICIO_DETALLE_CANTIDAD` DOUBLE(24,4) NOT NULL COMMENT 'Cantidad del articulo correspondiente al servicio',
	`SERVICIO_DETALLE_PRECIO` DOUBLE(24,4) NOT NULL COMMENT 'Precio correspondiente del articulo del servicio',
	`SERVICIO_DETALLE_OBSERVACION` VARCHAR(250) NULL COMMENT 'Observaciones sobre el articulo utilizado en el movimiento' COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.servicio_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `servicio_v` (
	`SERVICIO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del servicio',
	`SERVICIO_COMPROBANTE` VARCHAR(24) NOT NULL COMMENT 'Numero de comprobante del servicio' COLLATE 'utf8mb4_general_ci',
	`SERVICIO_TIMBRADO` INT(11) NOT NULL COMMENT 'Numero del timbrado del servicio',
	`SERVICIO_FECHA` VARCHAR(10) NULL COLLATE 'utf8mb4_general_ci',
	`SERVICIO_OBSERVACION` VARCHAR(250) NULL COMMENT 'Observaciones referentes al servicio' COLLATE 'utf8mb4_general_ci',
	`CLIENTE_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del cliente del servicio',
	`CLIENTE_DESCRIPCION` VARCHAR(201) NOT NULL COLLATE 'utf8mb4_general_ci',
	`USUARIO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del usuario que ingreso el servicio',
	`USUARIO_DESCRIPCION` VARCHAR(201) NOT NULL COLLATE 'utf8mb4_general_ci',
	`DEPOSITO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del deposito en el cual se moveran los gastos de los articulos utilizados en el servicio',
	`DEPOSITO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre del deposito' COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.timbrado_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `timbrado_v` (
	`TIMBRADO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del timbrado',
	`TIMBRADO_NUMERO` INT(11) NOT NULL COMMENT 'Numero de timbrado el cual se va a utilizar',
	`TIMBRADO_FECHA_INICIAL` VARCHAR(10) NULL COLLATE 'utf8mb4_general_ci',
	`TIMBRADO_FECHA_FINAL` VARCHAR(10) NULL COLLATE 'utf8mb4_general_ci',
	`TIMBRADO_NUMERO_DESDE` VARCHAR(7) NOT NULL COLLATE 'utf8mb4_general_ci',
	`TIMBRADO_NUMERO_HASTA` VARCHAR(7) NOT NULL COLLATE 'utf8mb4_general_ci',
	`TIMBRADO_ESTABLECIMIENTO` VARCHAR(3) NOT NULL COLLATE 'utf8mb4_general_ci',
	`TIMBRADO_PUNTO_EMISION` VARCHAR(3) NOT NULL COLLATE 'utf8mb4_general_ci',
	`TIPO_DOCUMENTO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del tipo de documento',
	`TIPO_DOCUMENTO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion del tipo de documento' COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.venta_detalle_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `venta_detalle_v` (
	`VENTA_DETALLE_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la venta relacionada',
	`VENTA_NUMERO_DOCUMENTO` VARCHAR(24) NOT NULL COMMENT 'Numero de comprobante de la venta' COLLATE 'utf8mb4_general_ci',
	`VENTA_NUMERO_TIMBRADO` INT(11) NOT NULL COMMENT 'Numero del timbrado de la venta',
	`VENTA_FECHA` VARCHAR(10) NULL COLLATE 'utf8mb4_general_ci',
	`VENTA_DETALLE_ARTICULO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del articulo relacionado',
	`ARTICULO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre del articulo' COLLATE 'utf8mb4_general_ci',
	`VENTA_DETALLE_NUMERO_ITEM` INT(11) NOT NULL COMMENT 'Numero de item al momento de ingresar la venta',
	`VENTA_DETALLE_CANTIDAD` DOUBLE(24,4) NOT NULL COMMENT 'Cantidad del articulo correspondiente de la venta',
	`VENTA_DETALLE_PRECIO` DOUBLE(24,4) NOT NULL COMMENT 'Precio correspondiente del articulo de venta'
) ENGINE=MyISAM;

-- Volcando estructura para vista taller_bd.venta_v
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `venta_v` (
	`VENTA_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion de la venta',
	`VENTA_NUMERO_DOCUMENTO` VARCHAR(24) NOT NULL COMMENT 'Numero de comprobante de la venta' COLLATE 'utf8mb4_general_ci',
	`VENTA_NUMERO_TIMBRADO` INT(11) NOT NULL COMMENT 'Numero del timbrado de la venta',
	`VENTA_FECHA` VARCHAR(10) NULL COLLATE 'utf8mb4_general_ci',
	`VENTA_OBSERVACION` VARCHAR(250) NULL COMMENT 'Observaciones referentes a la venta' COLLATE 'utf8mb4_general_ci',
	`CLIENTE_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del cliente de la venta',
	`CLIENTE_DESCRIPCION` VARCHAR(201) NOT NULL COLLATE 'utf8mb4_general_ci',
	`DEPOSITO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del deposito de la venta ',
	`DEPOSITO_DESCRIPCION` VARCHAR(100) NOT NULL COMMENT 'Descripcion o nombre del deposito' COLLATE 'utf8mb4_general_ci',
	`USUARIO_CODIGO` INT(11) NOT NULL COMMENT 'Codigo de identificacion del usuario que ingreso la venta',
	`USUARIO_DESCRIPCION` VARCHAR(201) NOT NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para procedimiento taller_bd.P_ACT_ITEM_DEP
DELIMITER //
CREATE PROCEDURE `P_ACT_ITEM_DEP`(
	IN `xIDARTICULO` INT,
	IN `xID` INT,
	IN `xCANTIDAD` DOUBLE,
	IN `xOPERACION` VARCHAR(1),
	IN `xTABLA` VARCHAR(100)
)
BEGIN
	DECLARE V_DEPOSITO INT;
	DECLARE V_CANTIDAD_ENTRADA DOUBLE;
	DECLARE V_CANTIDAD_SALIDA DOUBLE;
	
	IF xTABLA = 'compra' THEN
		SELECT iddeposito INTO V_DEPOSITO FROM compra  WHERE idcompra = xID;
	END IF;
	
	IF xTABLA = 'venta' THEN
		SELECT iddeposito INTO V_DEPOSITO FROM venta  WHERE idventa = xID;
	END IF;
	
	IF xTABLA = 'nota_credito_compra' THEN
		SELECT iddeposito INTO V_DEPOSITO FROM nota_credito_compra  WHERE idnota = xID;
	END IF;
	
	IF xTABLA = 'nota_debito_compra' THEN
		SELECT iddeposito INTO V_DEPOSITO FROM nota_debito_compra  WHERE idnota = xID;
	END IF;
	
	IF xTABLA = 'servicio' THEN
		SELECT iddeposito INTO V_DEPOSITO FROM servicio  WHERE idservicio = xID;
	END IF;
	
	IF xOPERACION = 'E' THEN
		SET V_CANTIDAD_ENTRADA 	= xCANTIDAD;
		SET V_CANTIDAD_SALIDA	= 0;
	END IF;
	
	IF xOPERACION = 'S' THEN
		SET V_CANTIDAD_ENTRADA 	= 0;
		SET V_CANTIDAD_SALIDA	= xCANTIDAD;
	END IF;
	
	CALL P_ACT_ITEM_DEP_INS_UPD(V_DEPOSITO, xIDARTICULO, V_CANTIDAD_ENTRADA, V_CANTIDAD_SALIDA);	
END//
DELIMITER ;

-- Volcando estructura para procedimiento taller_bd.P_ACT_ITEM_DEP_INS_UPD
DELIMITER //
CREATE PROCEDURE `P_ACT_ITEM_DEP_INS_UPD`(
	IN `xIDDEPOSITO` INT,
	IN `xIDARTICULO` INT,
	IN `xCANTIDAD_ENTRADA` DOUBLE,
	IN `xCANTIDAD_SALIDA` DOUBLE
)
BEGIN
	DECLARE V_REGISTROS INT;
	DECLARE V_TIPO_SERVICIO_CONFIGURACION INT;
	DECLARE V_TIPO_SERVICIO INT;
	
	-- SE CONSULTA LA CONFIGURACION DEL MODULO DE COMPRAS PARA VERIFICAR EL TIPO DE ARTICULO PARA SERVICIOS...
	SELECT tipo_articulo_servicio INTO V_TIPO_SERVICIO_CONFIGURACION FROM configuracion WHERE idconfiguracion = 1; 
	
	-- SE CONSULTA EL ARTICULO PARA VER QUE TIPO DE TIPO TIENE ASIGNADO
	SELECT idtipo INTO V_TIPO_SERVICIO FROM articulo WHERE idarticulo = xIDARTICULO;
	
	-- SE REALIZA LA VALIDACION DE SI ES TIPO SERVICIO O NO. EN EL CASO DE QUE NO SEA TIPO SERVICIO, NO SE REALIZARA NINGUNA ACTUALIZACION EN LOS DEPOSITOS.
	IF V_TIPO_SERVICIO_CONFIGURACION <> V_TIPO_SERVICIO THEN
		
		-- SE CONSULTA SI YA EXISTE EN LA BASE DE DATOS EL ARTICULO CON LOS LOTES Y VENCIMIENTOS
		SELECT COUNT(*) INTO V_REGISTROS FROM articulo_deposito
		WHERE idarticulo	= xIDARTICULO
		AND 	iddeposito	= xIDDEPOSITO;
	
		
		-- DEPENDIENDO DEL RESULTADO DE LA CONSULTA, AQUI SE INSERTA O SE ACTUALIZA
		IF V_REGISTROS = 0 THEN
			INSERT INTO articulo_deposito (idarticulo, iddeposito, existencia)
			VALUES(xIDARTICULO, xIDDEPOSITO, xCANTIDAD_ENTRADA - xCANTIDAD_SALIDA);
		ELSE
			UPDATE articulo_deposito 
			SET existencia = existencia + xCANTIDAD_ENTRADA - xCANTIDAD_SALIDA
			WHERE idarticulo	= xIDARTICULO
			AND	iddeposito	= xIDDEPOSITO;
		END IF;			
		
	END IF;
	

END//
DELIMITER ;

-- Volcando estructura para disparador taller_bd.TR_COMPRA_DETALLE_STOCK_DEL
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `TR_COMPRA_DETALLE_STOCK_DEL` BEFORE DELETE ON `compra_detalle` FOR EACH ROW BEGIN
	CALL P_ACT_ITEM_DEP(OLD.idarticulo, OLD.idcompra, OLD.cantidad, 'S', 'compra');
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador taller_bd.TR_COMPRA_DETALLE_STOCK_INS
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `TR_COMPRA_DETALLE_STOCK_INS` AFTER INSERT ON `compra_detalle` FOR EACH ROW BEGIN
	CALL P_ACT_ITEM_DEP(NEW.idarticulo, NEW.idcompra, NEW.cantidad, 'E', 'compra');
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador taller_bd.TR_NOTA_CREDITO_COMPRA_DETALLE_STOCK_DEL
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `TR_NOTA_CREDITO_COMPRA_DETALLE_STOCK_DEL` BEFORE DELETE ON `nota_credito_compra_detalle` FOR EACH ROW BEGIN
	CALL P_ACT_ITEM_DEP(OLD.idarticulo, OLD.idnota, OLD.cantidad, 'E', 'nota_credito_compra');
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador taller_bd.TR_NOTA_CREDITO_COMPRA_DETALLE_STOCK_INS
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `TR_NOTA_CREDITO_COMPRA_DETALLE_STOCK_INS` AFTER INSERT ON `nota_credito_compra_detalle` FOR EACH ROW BEGIN
	CALL P_ACT_ITEM_DEP(NEW.idarticulo, NEW.idnota, NEW.cantidad, 'S', 'nota_credito_compra');
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador taller_bd.TR_NOTA_DEBITO_COMPRA_DETALLE_STOCK_DEL
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `TR_NOTA_DEBITO_COMPRA_DETALLE_STOCK_DEL` BEFORE DELETE ON `nota_debito_compra_detalle` FOR EACH ROW BEGIN
	CALL P_ACT_ITEM_DEP(OLD.idarticulo, OLD.idnota, OLD.cantidad, 'S', 'nota_debito_compra');
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador taller_bd.TR_NOTA_DEBITO_COMPRA_DETALLE_STOCK_INS
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `TR_NOTA_DEBITO_COMPRA_DETALLE_STOCK_INS` AFTER INSERT ON `nota_debito_compra_detalle` FOR EACH ROW BEGIN
	CALL P_ACT_ITEM_DEP(NEW.idarticulo, NEW.idnota, NEW.cantidad, 'E', 'nota_debito_compra');
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador taller_bd.TR_SERVICIO_DETALLE_STOCK_DEL
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `TR_SERVICIO_DETALLE_STOCK_DEL` BEFORE DELETE ON `servicio_detalle` FOR EACH ROW BEGIN
	CALL P_ACT_ITEM_DEP(OLD.idarticulo, OLD.idservicio, OLD.cantidad, 'E', 'servicio');
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador taller_bd.TR_SERVICIO_DETALLE_STOCK_INS
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `TR_SERVICIO_DETALLE_STOCK_INS` AFTER INSERT ON `servicio_detalle` FOR EACH ROW BEGIN
	CALL P_ACT_ITEM_DEP(NEW.idarticulo, NEW.idservicio, NEW.cantidad, 'S', 'servicio');
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador taller_bd.TR_VENTA_DETALLE_STOCK_DEL
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `TR_VENTA_DETALLE_STOCK_DEL` BEFORE DELETE ON `venta_detalle` FOR EACH ROW BEGIN
	CALL P_ACT_ITEM_DEP(OLD.idarticulo, OLD.idventa, OLD.cantidad, 'E', 'venta');
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador taller_bd.TR_VENTA_DETALLE_STOCK_INS
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `TR_VENTA_DETALLE_STOCK_INS` AFTER INSERT ON `venta_detalle` FOR EACH ROW BEGIN
	CALL P_ACT_ITEM_DEP(NEW.idarticulo, NEW.idventa, NEW.cantidad, 'S', 'venta');
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para vista taller_bd.apertura_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `apertura_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `apertura_v` AS SELECT 
A.idapertura AS APERTURA_CODIGO,
A.idcaja AS CAJA_CODIGO,
C.descripcion AS CAJA_DESCRIPCION,
 DATE_FORMAT(A.fecha,'%d/%m/%Y %r') AS APERTURA_FECHA,
A.monto_inicial AS APERTURA_MONTO_INICIAL,
A.observacion AS APERTURA_OBSERVACION,
CASE
	WHEN A.estado = 'A' THEN 'ABIERTO'
	WHEN A.estado = 'C' THEN 'CERRADO'
	ELSE 'INDEFINIDO'
END APERTURA_ESTADO,
A.idusuario AS USUARIO_CODIGO,
CONCAT(U.nombre,' ',U.apellido) AS USUARIO_DESCRIPCION
FROM apertura AS A
INNER JOIN caja AS C ON C.idcaja = A.idcaja
INNER JOIN usuario AS U ON U.idusuario = A.idusuario
ORDER BY A.idapertura DESC ;

-- Volcando estructura para vista taller_bd.articulo_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `articulo_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `articulo_v` AS SELECT
A.idarticulo AS ARTICULO_CODIGO,
A.descripcion AS ARTICULO_DESCRIPCION,
A.referencia AS ARTICULO_REFERENCIA,
CASE
	WHEN A.estado = 'A' THEN 'ACTIVO'
	WHEN A.estado = 'I' THEN 'INACTIVO'
	ELSE 'INDEFINIDO'
END ARTICULO_ESTADO,
A.idmarca AS MARCA_CODIGO,
M.descripcion AS MARCA_DESCRIPCION,
A.idlinea AS LINEA_CODIGO,
L.descripcion AS LINEA_DESCRIPCION,
A.idunidad AS  UNIDAD_MEDIDA_CODIGO,
UM.abreviacion AS UNIDAD_MEDIDA_SIMBOLO,
A.idtipo AS TIPO_ARTICULO_CODIGO,
TA.descripcion AS TIPO_ARTICULO_DESCRIPCION,
IFNULL(A.costo, 0) AS ARTICULO_COSTO,
IFNULL(A.precio, 0) AS ARTICULO_PRECIO,
A.idimpuesto AS IMPUESTO_CODIGO,
I.descripcion AS IMPUESTO_DESCRIPCION
FROM articulo AS A 
INNER JOIN marca AS M ON M.idmarca = A.idmarca
INNER JOIN linea AS L ON L.idlinea = A.idlinea
INNER JOIN unidad_medida AS UM ON UM.idunidad = A.idunidad
INNER JOIN tipo_articulo AS TA ON TA.idtipo = A.idtipo 
INNER JOIN impuesto AS I ON I.idimpuesto = A.idimpuesto
ORDER BY A.descripcion ;

-- Volcando estructura para vista taller_bd.ciudad_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `ciudad_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `ciudad_v` AS SELECT
C.idciudad AS CIUDAD_CODIGO,
C.descripcion AS CIUDAD_DESCRIPCION,
C.idpais AS PAIS_CODIGO,
P.descripcion AS PAIS_DESCRIPCION
FROM ciudad AS C
INNER JOIN pais AS P ON P.idpais = C.idpais
ORDER BY C.descripcion ;

-- Volcando estructura para vista taller_bd.cliente_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `cliente_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `cliente_v` AS SELECT
C.idcliente AS CLIENTE_CODIGO,
CONCAT(C.nombre,' ',C.apellido) AS CLIENTE_NOMBRE,
C.cedula AS CLIENTE_CEDULA,
C.telefono AS CLIENTE_TELEFONO,
C.direccion AS CLIENTE_DIRECCION,
C.estado AS CLIENTE_ESTADO,
C.idciudad AS CIUDAD_CODIGO,
CI.descripcion AS CIUDAD_DESCRIPCION
FROM cliente AS C 
INNER JOIN ciudad AS CI ON CI.idciudad = C.idciudad
ORDER BY CONCAT(C.nombre,' ',C.apellido) ;

-- Volcando estructura para vista taller_bd.compra_detalle_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `compra_detalle_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `compra_detalle_v` AS SELECT
CD.idcompra AS COMPRA_DETALLE_CODIGO,
C.numero_documento AS COMPRA_NUMERO_DOCUMENTO,
C.numero_timbrado AS COMPRA_NUMERO_TIMBRADO,
DATE_FORMAT(C.fecha,'%d/%m/%Y') AS COMPRA_FECHA,
CD.idarticulo AS COMPRA_DETALLE_ARTICULO,
A.descripcion AS ARTICULO_DESCRIPCION,
CD.numero_item AS COMPRA_DETALLE_NUMERO_ITEM,
CD.cantidad AS COMPRA_DETALLE_CANTIDAD,
CD.costo AS COMPRA_DETALLE_PRECIO
FROM compra_detalle AS CD
INNER JOIN compra AS C ON C.idcompra = CD.idcompra
INNER JOIN articulo AS A ON A.idarticulo = CD.idarticulo
ORDER BY CD.idcompra, CD.idarticulo ;

-- Volcando estructura para vista taller_bd.compra_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `compra_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `compra_v` AS SELECT
C.idcompra AS COMPRA_CODIGO,
C.numero_documento AS COMPRA_NUMERO_DOCUMENTO,
C.numero_timbrado AS COMPRA_NUMERO_TIMBRADO,
C.idproveedor AS PROVEEDOR_CODIGO,
P.razon_social AS PROVEEDOR_DESCRIPCION,
DATE_FORMAT(C.fecha,'%d/%m/%Y') AS COMPRA_FECHA
FROM compra AS C
INNER JOIN proveedor AS P ON P.idproveedor = C.idproveedor
ORDER BY C.idcompra ;

-- Volcando estructura para vista taller_bd.configuracion_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `configuracion_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `configuracion_v` AS SELECT 
C.idconfiguracion AS CONFIGURACION_CODIGO, 
C.tipo_articulo_servicio AS TIPO_ARTICULO_CODIGO, 
TA.descripcion AS TIPO_SERVICIO_DESCRIPCION,
C.cantidad_maxima_facturacion AS CANTIDAD_MAXIMA_FACTURACION, 
C.facturacion_timbrado AS FACTURACION_TIMBRADO_CODIGO, 
TDF.descripcion AS FT_TIPO_DOCUMENTO_DESCRIPCION,
C.nota_credito_timbrado AS NOTA_CREDITO_TIMBRADO_CODIGO, 
TDNC.descripcion AS NC_TIPO_DOCUMENTO_DESCRIPCION,
C.nota_debito_timbrado AS NOTA_DEBITO_TIMBRADO_CODIGO,
TDND.descripcion AS ND_TIPO_DOCUMENTO_DESCRIPCION
FROM configuracion AS C
INNER JOIN tipo_articulo AS TA ON TA.idtipo = C.tipo_articulo_servicio
INNER JOIN timbrado AS FT ON FT.idtimbrado = C.facturacion_timbrado
INNER JOIN timbrado AS NC ON NC.idtimbrado = C.nota_credito_timbrado
INNER JOIN timbrado AS ND ON ND.idtimbrado = C.nota_debito_timbrado
INNER JOIN TIPO_DOCUMENTO AS TDF ON TDF.idtipo = FT.idtipo
INNER JOIN TIPO_DOCUMENTO AS TDNC ON TDNC.idtipo = NC.idtipo
INNER JOIN TIPO_DOCUMENTO AS TDND ON TDND.idtipo = ND.idtipo
ORDER BY C.idconfiguracion ;

-- Volcando estructura para vista taller_bd.empleado_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `empleado_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `empleado_v` AS SELECT e.idempleado EMPL_CODIGO,
       CONCAT(e.nombre,' ',apellido) EMPL_DESCRIPCION,
		 e.cedula EMPL_CI,
		 e.telefono EMPL_TEL,
		 e.direccion EMPL_DIRECCION,
		 e.estado EMPL_ESTADO,
		 e.idciudad EMPL_COI_COD,
		 c.descripcion EMPL_CIUDAD
  FROM empleado e, ciudad c
 WHERE e.idciudad = c.idciudad ; ;

-- Volcando estructura para vista taller_bd.orden_trabajo_detalle_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `orden_trabajo_detalle_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `orden_trabajo_detalle_v` AS SELECT
OTD.idorden AS ORDEN_DETALLE_CODIGO,
OT.numero_comprobante AS ORDEN_NUMERO_DOCUMENTO,
DATE_FORMAT(OT.fecha,'%d/%m/%Y') AS ORDEN_FECHA,
OTD.idarticulo AS ORDEN_DETALLE_ARTICULO,
A.descripcion AS ARTICULO_DESCRIPCION,
OTD.numero_item AS ORDEN_DETALLE_NUMERO_ITEM,
OTD.cantidad AS ORDEN_DETALLE_CANTIDAD,
OTD.precio AS ORDEN_DETALLE_PRECIO,
OTD.observacion AS ORDEN_OBSERVACION
FROM orden_trabajo_detalle AS OTD
INNER JOIN orden_trabajo AS OT ON OT.idorden = OTD.idorden
INNER JOIN articulo AS A ON A.idarticulo = OTD.idarticulo
ORDER BY OTD.idorden, OTD.idarticulo ;

-- Volcando estructura para vista taller_bd.orden_trabajo_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `orden_trabajo_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `orden_trabajo_v` AS SELECT
OT.idorden AS ORDEN_CODIGO,
OT.numero_comprobante AS ORDEN_COMPROBANTE,
DATE_FORMAT(OT.fecha,'%d/%m/%Y')  AS ORDEN_FECHA,
OT.observacion AS ORDEN_OBSERVACION,
CASE
	WHEN OT.estado = 'AP' THEN 'APROBADO PARCIAL'
	WHEN OT.estado = 'R' THEN 'RECHAZADO'
	WHEN OT.estado = 'C' THEN 'CONFIRMADO'
	WHEN OT.estado = 'P' THEN 'PENDIENTE'
	ELSE 'INDEFINIDO'
END ORDEN_ESTADO,
OT.idempleado AS ORDEN_EMPLEADO,
CONCAT(E.nombre,' ',E.apellido) AS EMPLEADO_DESCRIPCION,
OT.idcliente AS ORDEN_CLIENTE,
CONCAT(C.nombre,' ',C.apellido) AS CLIENTE_DESCRIPCION,
OT.idusuario AS ORDEN_USUARIO,
CONCAT(U.nombre,' ',U.apellido) AS USUARIO_DESCRIPCION
FROM orden_trabajo AS OT
INNER JOIN cliente AS C ON C.idcliente = OT.idcliente
INNER JOIN empleado AS E ON E.idempleado = OT.idempleado
INNER JOIN usuario AS U ON U.idusuario = OT.idusuario
ORDER BY OT.idorden ;

-- Volcando estructura para vista taller_bd.proveedor_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `proveedor_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `proveedor_v` AS SELECT
P.idproveedor AS PROVEEDOR_CODIGO,
P.razon_social AS PROVEEDOR_DESCRIPCION,
P.ruc AS PROVEEDOR_RUC,
P.representante_legal AS PROVEEDOR_REPRESENTANTE,
P.telefono AS PROVEEDOR_TELEFONO,
P.direccion AS PROVEEDOR_DIRECCION,
P.estado AS PROVEEDOR_ESTADO,
P.idciudad AS CIUDAD_CODIGO,
C.descripcion AS CIUDAD_DESCRIPCION
FROM proveedor AS P
INNER JOIN ciudad AS C ON C.idciudad = P.idproveedor
ORDER BY P.razon_social ;

-- Volcando estructura para vista taller_bd.servicio_detalle_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `servicio_detalle_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `servicio_detalle_v` AS SELECT
SD.idservicio AS SERVICIO_DETALLE_CODIGO,
SD.idarticulo AS ARTICULO_CODIGO,
A.descripcion AS ARTICULO_DESCRIPCION,
SD.numero_item AS SERVICIO_DETALLE_ITEM,
SD.cantidad AS SERVICIO_DETALLE_CANTIDAD,
SD.precio AS SERVICIO_DETALLE_PRECIO,
SD.observacion AS SERVICIO_DETALLE_OBSERVACION
FROM servicio_detalle AS SD
INNER JOIN articulo AS A ON A.idarticulo = SD.idarticulo
ORDER BY SD.idservicio ;

-- Volcando estructura para vista taller_bd.servicio_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `servicio_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `servicio_v` AS SELECT
S.idservicio AS SERVICIO_CODIGO,
S.numero_documento AS SERVICIO_COMPROBANTE,
S.numero_timbrado AS SERVICIO_TIMBRADO,
DATE_FORMAT(S.fecha,'%d/%m/%Y') AS SERVICIO_FECHA,
S.observacion AS SERVICIO_OBSERVACION,
S.idcliente AS CLIENTE_CODIGO,
CONCAT(C.nombre,' ',C.apellido) AS CLIENTE_DESCRIPCION,
S.idusuario AS USUARIO_CODIGO,
CONCAT(U.nombre,' ',U.apellido) AS USUARIO_DESCRIPCION,
S.iddeposito AS DEPOSITO_CODIGO,
D.descripcion AS DEPOSITO_DESCRIPCION
FROM servicio AS S
INNER JOIN cliente AS C ON C.idcliente = S.idcliente
INNER JOIN usuario AS U ON U.idusuario = S.idusuario
INNER JOIN deposito AS D ON D.iddeposito = S.iddeposito
ORDER BY S.idservicio ;

-- Volcando estructura para vista taller_bd.timbrado_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `timbrado_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `timbrado_v` AS SELECT 
T.idtimbrado AS TIMBRADO_CODIGO, 
T.timbrado AS TIMBRADO_NUMERO, 
DATE_FORMAT(T.fecha_inicial,'%d/%m/%Y') AS TIMBRADO_FECHA_INICIAL, 
DATE_FORMAT(T.fecha_final,'%d/%m/%Y') AS TIMBRADO_FECHA_FINAL, 
LPAD(T.numero_desde,7,0) AS TIMBRADO_NUMERO_DESDE, 
LPAD(T.numero_hasta,7,0) AS TIMBRADO_NUMERO_HASTA, 
LPAD(T.establecimiento,3,0) AS TIMBRADO_ESTABLECIMIENTO, 
LPAD(T.punto_emision,3,0) AS TIMBRADO_PUNTO_EMISION, 
T.idtipo AS TIPO_DOCUMENTO_CODIGO,
TD.descripcion AS TIPO_DOCUMENTO_DESCRIPCION	
FROM timbrado AS T
INNER JOIN tipo_documento AS TD ON TD.idtipo = T.idtipo
ORDER BY T.idtimbrado ;

-- Volcando estructura para vista taller_bd.venta_detalle_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `venta_detalle_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `venta_detalle_v` AS SELECT
VD.idventa AS VENTA_DETALLE_CODIGO,
V.numero_documento AS VENTA_NUMERO_DOCUMENTO,
V.numero_timbrado AS VENTA_NUMERO_TIMBRADO,
DATE_FORMAT(V.fecha,'%d/%m/%Y') AS VENTA_FECHA,
VD.idarticulo VENTA_DETALLE_ARTICULO,
A.descripcion AS ARTICULO_DESCRIPCION,
VD.numero_item AS VENTA_DETALLE_NUMERO_ITEM,
VD.cantidad AS VENTA_DETALLE_CANTIDAD,
VD.precio AS VENTA_DETALLE_PRECIO
FROM venta_detalle AS VD
INNER JOIN venta AS V ON V.idventa = VD.idventa
INNER JOIN articulo AS A ON A.idarticulo = VD.idarticulo
ORDER BY VD.idventa, VD.idarticulo ;

-- Volcando estructura para vista taller_bd.venta_v
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `venta_v`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `venta_v` AS SELECT 
V.idventa AS VENTA_CODIGO, 
V.numero_documento AS VENTA_NUMERO_DOCUMENTO, 
V.numero_timbrado AS VENTA_NUMERO_TIMBRADO, 
DATE_FORMAT(V.fecha,'%d/%m/%Y') AS VENTA_FECHA, 
V.observacion AS VENTA_OBSERVACION, 
V.idcliente AS CLIENTE_CODIGO, 
CONCAT(C.nombre,' ',C.apellido) AS CLIENTE_DESCRIPCION,
V.iddeposito AS DEPOSITO_CODIGO, 
D.descripcion AS DEPOSITO_DESCRIPCION,
V.idusuario AS USUARIO_CODIGO,
CONCAT(U.nombre,' ',U.apellido) AS USUARIO_DESCRIPCION
FROM venta AS V
INNER JOIN cliente AS C ON C.idcliente = V.idventa
INNER JOIN deposito AS D ON D.iddeposito = V.iddeposito
INNER JOIN usuario AS U ON U.idusuario = V.idusuario
ORDER BY V.idventa ;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
