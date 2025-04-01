import pandas as pd

#Hacer un ejemplo de carga de archivo y aplicar min, max, media y desviacion estandar

def cotizacion(fichero):
    df = pd.read_csv(fichero, sep=';', decimal=',', thousands='.', index_col=0)
    return pd.DataFrame([df.min(), df.max(), df.mean(), df.std()], index = ['Minimo', 'Maximo', 'Media', 'Desviación estandar'])

print(cotizacion(r'C:\Users\HP\Desktop\CARRILLO_SANDOVAL_KRIZIA_6IV7\AnalisisDeDatos\EstadisticaDescriptiva\Tablas\cotizacion.csv'))