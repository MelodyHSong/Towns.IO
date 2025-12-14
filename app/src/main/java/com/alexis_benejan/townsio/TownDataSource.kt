/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: TownDataSource.kt
☆ Date: 2025-12-13
☆
*/

package com.alexis_benejan.townsio

// ☆ Description: Lista completa de los 78 municipios de Puerto Rico.
// ☆ Nota: Se asume que los 156 recursos de drawables existen en 'res/drawable/'.

object TownDataSource {

    fun getTowns(): List<Town> {
        return listOf(
            // Región Norte (Metro & Costa Norte)
            Town(id = 1, name = "San Juan", region = "Norte", cognomento = "La Ciudad Amurallada", flagResource = R.drawable.norte_san_juan_flag, shieldResource = R.drawable.norte_san_juan_shield),
            Town(id = 2, name = "Bayamón", region = "Norte", cognomento = "El Pueblo del Chicharrón", flagResource = R.drawable.norte_bayamon_flag, shieldResource = R.drawable.norte_bayamon_shield),
            Town(id = 3, name = "Carolina", region = "Norte", cognomento = "Tierra de Gigantes", flagResource = R.drawable.norte_carolina_flag, shieldResource = R.drawable.norte_carolina_shield),
            Town(id = 4, name = "Guaynabo", region = "Norte", cognomento = "Capital del Deporte", flagResource = R.drawable.norte_guaynabo_flag, shieldResource = R.drawable.norte_guaynabo_shield),
            Town(id = 5, name = "Cataño", region = "Norte", cognomento = "La Antesala de la Capital", flagResource = R.drawable.norte_catano_flag, shieldResource = R.drawable.norte_catano_shield),
            Town(id = 6, name = "Toa Baja", region = "Norte", cognomento = "La Ciudad Llanera", flagResource = R.drawable.norte_toa_baja_flag, shieldResource = R.drawable.norte_toa_baja_shield),
            Town(id = 7, name = "Toa Alta", region = "Norte", cognomento = "Cuna de Poetas", flagResource = R.drawable.norte_toa_alta_flag, shieldResource = R.drawable.norte_toa_alta_shield),
            Town(id = 8, name = "Dorado", region = "Norte", cognomento = "El Paraíso de Puerto Rico", flagResource = R.drawable.norte_dorado_flag, shieldResource = R.drawable.norte_dorado_shield),
            Town(id = 9, name = "Vega Alta", region = "Norte", cognomento = "Pueblo de los Ñangotaos", flagResource = R.drawable.norte_vega_alta_flag, shieldResource = R.drawable.norte_vega_alta_shield),
            Town(id = 10, name = "Vega Baja", region = "Norte", cognomento = "Ciudad del Melao Melao", flagResource = R.drawable.norte_vega_baja_flag, shieldResource = R.drawable.norte_vega_baja_shield),
            Town(id = 11, name = "Manatí", region = "Norte", cognomento = "La Atenas de Puerto Rico", flagResource = R.drawable.norte_manati_flag, shieldResource = R.drawable.norte_manati_shield),
            Town(id = 12, name = "Barceloneta", region = "Norte", cognomento = "La Ciudad de las Piñas", flagResource = R.drawable.norte_barceloneta_flag, shieldResource = R.drawable.norte_barceloneta_shield),
            Town(id = 13, name = "Florida", region = "Norte", cognomento = "Tierra del Río Encantado", flagResource = R.drawable.norte_florida_flag, shieldResource = R.drawable.norte_florida_shield),
            Town(id = 14, name = "Arecibo", region = "Norte", cognomento = "La Villa del Capitán Correa", flagResource = R.drawable.norte_arecibo_flag, shieldResource = R.drawable.norte_arecibo_shield),
            Town(id = 15, name = "Hatillo", region = "Norte", cognomento = "Capital de la Industria Lechera", flagResource = R.drawable.norte_hatillo_flag, shieldResource = R.drawable.norte_hatillo_shield),
            Town(id = 16, name = "Camuy", region = "Norte", cognomento = "La Ciudad Romántica", flagResource = R.drawable.norte_camuy_flag, shieldResource = R.drawable.norte_camuy_shield),
            Town(id = 17, name = "Quebradillas", region = "Norte", cognomento = "La Guarida del Pirata", flagResource = R.drawable.norte_quebradillas_flag, shieldResource = R.drawable.norte_quebradillas_shield),
            Town(id = 18, name = "Trujillo Alto", region = "Norte", cognomento = "Pueblo de los Arrecostaos", flagResource = R.drawable.norte_trujillo_alto_flag, shieldResource = R.drawable.norte_trujillo_alto_shield),

            // Región Este
            Town(id = 19, name = "Loíza", region = "Este", cognomento = "Capital de la Tradición", flagResource = R.drawable.este_loiza_flag, shieldResource = R.drawable.este_loiza_shield),
            Town(id = 20, name = "Canóvanas", region = "Este", cognomento = "La Ciudad de los Indios", flagResource = R.drawable.este_canovanas_flag, shieldResource = R.drawable.este_canovanas_shield),
            Town(id = 21, name = "Río Grande", region = "Este", cognomento = "Ciudad del Yunque", flagResource = R.drawable.este_rio_grande_flag, shieldResource = R.drawable.este_rio_grande_shield),
            Town(id = 22, name = "Luquillo", region = "Este", cognomento = "La Capital del Sol", flagResource = R.drawable.este_luquillo_flag, shieldResource = R.drawable.este_luquillo_shield),
            Town(id = 23, name = "Fajardo", region = "Este", cognomento = "Los Cariduros", flagResource = R.drawable.este_fajardo_flag, shieldResource = R.drawable.este_fajardo_shield),
            Town(id = 24, name = "Ceiba", region = "Este", cognomento = "La Ciudad del Marlin", flagResource = R.drawable.este_ceiba_flag, shieldResource = R.drawable.este_ceiba_shield),
            Town(id = 25, name = "Naguabo", region = "Este", cognomento = "El Pueblo de los Enchumbaos", flagResource = R.drawable.este_naguabo_flag, shieldResource = R.drawable.este_naguabo_shield),
            Town(id = 26, name = "Humacao", region = "Este", cognomento = "La Ciudad Gris", flagResource = R.drawable.este_humacao_flag, shieldResource = R.drawable.este_humacao_shield),
            Town(id = 27, name = "Yabucoa", region = "Este", cognomento = "La Ciudad del Azúcar", flagResource = R.drawable.este_yabucoa_flag, shieldResource = R.drawable.este_yabucoa_shield),
            Town(id = 28, name = "Maunabo", region = "Este", cognomento = "La Ciudad Jueyera", flagResource = R.drawable.este_maunabo_flag, shieldResource = R.drawable.este_maunabo_shield),
            Town(id = 29, name = "Vieques", region = "Este", cognomento = "La Isla Nena", flagResource = R.drawable.este_vieques_flag, shieldResource = R.drawable.este_vieques_shield),
            Town(id = 30, name = "Culebra", region = "Este", cognomento = "Isla Chiquita", flagResource = R.drawable.este_culebra_flag, shieldResource = R.drawable.este_culebra_shield),
            Town(id = 31, name = "Juncos", region = "Este", cognomento = "La Ciudad del Valenciano", flagResource = R.drawable.este_juncos_flag, shieldResource = R.drawable.este_juncos_shield),

            // Región Sur
            Town(id = 32, name = "Arroyo", region = "Sur", cognomento = "El Pueblo Grato", flagResource = R.drawable.sur_arroyo_flag, shieldResource = R.drawable.sur_arroyo_shield),
            Town(id = 33, name = "Patillas", region = "Sur", cognomento = "La Esmeralda del Sur", flagResource = R.drawable.sur_patillas_flag, shieldResource = R.drawable.sur_patillas_shield),
            Town(id = 34, name = "Guayama", region = "Sur", cognomento = "La Ciudad Bruja", flagResource = R.drawable.sur_guayama_flag, shieldResource = R.drawable.sur_guayama_shield),
            Town(id = 35, name = "Salinas", region = "Sur", cognomento = "El Pueblo del Mojo Isleño", flagResource = R.drawable.sur_salinas_flag, shieldResource = R.drawable.sur_salinas_shield),
            Town(id = 36, name = "Santa Isabel", region = "Sur", cognomento = "Tierra de Campeones", flagResource = R.drawable.sur_santa_isabel_flag, shieldResource = R.drawable.sur_santa_isabel_shield),
            Town(id = 37, name = "Coamo", region = "Sur", cognomento = "La Villa de San Blas", flagResource = R.drawable.sur_coamo_flag, shieldResource = R.drawable.sur_coamo_shield),
            Town(id = 38, name = "Juana Díaz", region = "Sur", cognomento = "La Ciudad del Maví", flagResource = R.drawable.sur_juana_diaz_flag, shieldResource = R.drawable.sur_juana_diaz_shield),
            Town(id = 39, name = "Ponce", region = "Sur", cognomento = "La Perla del Sur", flagResource = R.drawable.sur_ponce_flag, shieldResource = R.drawable.sur_ponce_shield),
            Town(id = 40, name = "Villalba", region = "Sur", cognomento = "La Ciudad del Avancino", flagResource = R.drawable.sur_villalba_flag, shieldResource = R.drawable.sur_villalba_shield),
            Town(id = 41, name = "Peñuelas", region = "Sur", cognomento = "El Valle de los Flamboyanes", flagResource = R.drawable.sur_penuelas_flag, shieldResource = R.drawable.sur_penuelas_shield),
            Town(id = 42, name = "Guayanilla", region = "Sur", cognomento = "La Villa de los Pescadores", flagResource = R.drawable.sur_guayanilla_flag, shieldResource = R.drawable.sur_guayanilla_shield),
            Town(id = 43, name = "Yauco", region = "Sur", cognomento = "El Pueblo del Café", flagResource = R.drawable.sur_yauco_flag, shieldResource = R.drawable.sur_yauco_shield),
            Town(id = 44, name = "Guánica", region = "Sur", cognomento = "El Pueblo de la Amistad", flagResource = R.drawable.sur_guanica_flag, shieldResource = R.drawable.sur_guanica_shield),
            Town(id = 45, name = "Lajas", region = "Sur", cognomento = "La Ciudad Cardenalicia", flagResource = R.drawable.sur_lajas_flag, shieldResource = R.drawable.sur_lajas_shield),
            Town(id = 46, name = "Sabana Grande", region = "Sur", cognomento = "Pueblo de los Petateros", flagResource = R.drawable.sur_sabana_grande_flag, shieldResource = R.drawable.sur_sabana_grande_shield),

            // Región Oeste
            Town(id = 47, name = "Cabo Rojo", region = "Oeste", cognomento = "Cuna de Betances", flagResource = R.drawable.oeste_cabo_rojo_flag, shieldResource = R.drawable.oeste_cabo_rojo_shield),
            Town(id = 48, name = "Mayagüez", region = "Oeste", cognomento = "La Sultana del Oeste", flagResource = R.drawable.oeste_mayaguez_flag, shieldResource = R.drawable.oeste_mayaguez_shield),
            Town(id = 49, name = "Hormigueros", region = "Oeste", cognomento = "El Pueblo del Milagro", flagResource = R.drawable.oeste_hormigueros_flag, shieldResource = R.drawable.oeste_hormigueros_shield),
            Town(id = 50, name = "Añasco", region = "Oeste", cognomento = "Donde los Dioses Murieron", flagResource = R.drawable.oeste_anasco_flag, shieldResource = R.drawable.oeste_anasco_shield),
            Town(id = 51, name = "Rincón", region = "Oeste", cognomento = "Pueblo de los Bellos Atardeceres", flagResource = R.drawable.oeste_rincon_flag, shieldResource = R.drawable.oeste_rincon_shield),
            Town(id = 52, name = "Aguada", region = "Oeste", cognomento = "La Villa de San Francisco", flagResource = R.drawable.oeste_aguada_flag, shieldResource = R.drawable.oeste_aguada_shield),
            Town(id = 53, name = "Aguadilla", region = "Oeste", cognomento = "El Jardín del Atlántico", flagResource = R.drawable.oeste_aguadilla_flag, shieldResource = R.drawable.oeste_aguadilla_shield),
            Town(id = 54, name = "Moca", region = "Oeste", cognomento = "La Capital del Mundillo", flagResource = R.drawable.oeste_moca_flag, shieldResource = R.drawable.oeste_moca_shield),
            Town(id = 55, name = "Isabela", region = "Oeste", cognomento = "El Jardín del Noroeste", flagResource = R.drawable.oeste_isabela_flag, shieldResource = R.drawable.oeste_isabela_shield),
            Town(id = 56, name = "San Sebastián", region = "Oeste", cognomento = "Cuna de la Hamaca", flagResource = R.drawable.oeste_san_sebastian_flag, shieldResource = R.drawable.oeste_san_sebastian_shield),
            Town(id = 57, name = "San Germán", region = "Oeste", cognomento = "La Ciudad de las Lomas", flagResource = R.drawable.oeste_san_german_flag, shieldResource = R.drawable.oeste_san_german_shield),

            // Región Central
            Town(id = 58, name = "Cidra", region = "Central", cognomento = "Pueblo de la Eterna Primavera", flagResource = R.drawable.central_cidra_flag, shieldResource = R.drawable.central_cidra_shield),
            Town(id = 59, name = "Comerío", region = "Central", cognomento = "La Perla del Plata", flagResource = R.drawable.central_comerio_flag, shieldResource = R.drawable.central_comerio_shield),
            Town(id = 60, name = "Naranjito", region = "Central", cognomento = "El Pueblo de los Changos", flagResource = R.drawable.central_naranjito_flag, shieldResource = R.drawable.central_naranjito_shield),
            Town(id = 61, name = "Corozal", region = "Central", cognomento = "Capital del Voleibol", flagResource = R.drawable.central_corozal_flag, shieldResource = R.drawable.central_corozal_shield),
            Town(id = 62, name = "Orocovis", region = "Central", cognomento = "Corazón de Puerto Rico", flagResource = R.drawable.central_orocovis_flag, shieldResource = R.drawable.central_orocovis_shield),
            Town(id = 63, name = "Barranquitas", region = "Central", cognomento = "Cuna de Próceres", flagResource = R.drawable.central_barranquitas_flag, shieldResource = R.drawable.central_barranquitas_shield),
            Town(id = 64, name = "Aibonito", region = "Central", cognomento = "La Ciudad de las Flores", flagResource = R.drawable.central_aibonito_flag, shieldResource = R.drawable.central_aibonito_shield),
            Town(id = 65, name = "Ciales", region = "Central", cognomento = "La Ciudad de la Cohoba", flagResource = R.drawable.central_ciales_flag, shieldResource = R.drawable.central_ciales_shield),
            Town(id = 66, name = "Morovis", region = "Central", cognomento = "La Isla Menos Conocida", flagResource = R.drawable.central_morovis_flag, shieldResource = R.drawable.central_morovis_shield),
            Town(id = 67, name = "Utuado", region = "Central", cognomento = "La Ciudad del Viví", flagResource = R.drawable.central_utuado_flag, shieldResource = R.drawable.central_utuado_shield),
            Town(id = 68, name = "Lares", region = "Central", cognomento = "Ciudad del Grito", flagResource = R.drawable.central_lares_flag, shieldResource = R.drawable.central_lares_shield),
            Town(id = 69, name = "Adjuntas", region = "Central", cognomento = "Ciudad del Gigante Dormido", flagResource = R.drawable.central_adjuntas_flag, shieldResource = R.drawable.central_adjuntas_shield),
            Town(id = 70, name = "Jayuya", region = "Central", cognomento = "La Capital Indígena", flagResource = R.drawable.central_jayuya_flag, shieldResource = R.drawable.central_jayuya_shield),
            Town(id = 71, name = "Maricao", region = "Central", cognomento = "Pueblo de las Indieras", flagResource = R.drawable.central_maricao_flag, shieldResource = R.drawable.central_maricao_shield),
            Town(id = 72, name = "Las Marías", region = "Central", cognomento = "Pueblo de la China Dulce", flagResource = R.drawable.central_las_marias_flag, shieldResource = R.drawable.central_las_marias_shield),
            Town(id = 73, name = "Gurabo", region = "Central", cognomento = "Pueblo de las Escaleras", flagResource = R.drawable.central_gurabo_flag, shieldResource = R.drawable.central_gurabo_shield),
            Town(id = 74, name = "San Lorenzo", region = "Central", cognomento = "Pueblo de los Samaritanos", flagResource = R.drawable.central_san_lorenzo_flag, shieldResource = R.drawable.central_san_lorenzo_shield),
            Town(id = 75, name = "Aguas Buenas", region = "Central", cognomento = "Ciudad de las Aguas Claras", flagResource = R.drawable.central_aguas_buenas_flag, shieldResource = R.drawable.central_aguas_buenas_shield),
            Town(id = 76, name = "Cayey", region = "Central", cognomento = "Ciudad de las Brumas", flagResource = R.drawable.central_cayey_flag, shieldResource = R.drawable.central_cayey_shield),
            Town(id = 77, name = "Caguas", region = "Central", cognomento = "La Ciudad Criolla", flagResource = R.drawable.central_caguas_flag, shieldResource = R.drawable.central_caguas_shield),
            Town(id = 78, name = "Las Piedras", region = "Central", cognomento = "Ciudad de los Artesanos", flagResource = R.drawable.central_las_piedras_flag, shieldResource = R.drawable.central_las_piedras_shield),
        )
    }
}