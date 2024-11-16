package dev.xget.freetogame.data.remote.utils

import androidx.compose.runtime.Stable

object GameApiConstants {

    fun getGameCategories() : List<String>  = GameTags
    @Stable
    val GameTags = listOf(
        "mmorpg",
        "shooter",
        "strategy",
        "moba",
        "racing",
        "sports",
        "social",
        "sandbox",
        "open-world",
        "survival",
        "pvp",
        "pve",
        "pixel",
        "voxel",
        "zombie",
        "turn-based",
        "first-person",
        "third-Person",
        "top-down",
        "tank",
        "space",
        "sailing",
        "side-scroller",
        "superhero",
        "permadeath",
        "card",
        "battle-royale",
        "mmo",
        "mmofps",
        "mmotps",
        "3d",
        "2d",
        "anime",
        "fantasy",
        "sci-fi",
        "fighting",
        "action-rpg",
        "action",
        "military",
        "martial-arts",
        "flight",
        "low-spec",
        "tower-defense",
        "horror",
        "mmorts"
    )

    @Stable
    val GamePlatforms = mapOf(
        "browser" to "Navegador",
        "pc" to "PC"
    )

    @Stable
    val GameSortOptions = mapOf(
        "release-date" to "Fecha de Lanzamiento",
        "alphabetical" to "Alfabético",
    )
}