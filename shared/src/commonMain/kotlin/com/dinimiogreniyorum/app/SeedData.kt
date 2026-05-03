package com.dinimiogreniyorum.app

class SeedData(private val repository: QuestionRepository) {

    fun seedIfEmpty() {
        val existing = repository.getQuestionsByCategory("TRUE_FALSE")
        if (existing.isNotEmpty()) return

        seedTrueFalse()
        seedEasyQuestions()
        seedMediumQuestions()
        seedHardQuestions()
        seedSurahQuestions()
        seedSurahTest()
    }

    private fun seedTrueFalse() {
        val questions = listOf(
            Triple("Namaz günde 5 vakit kılınır.", "Doğru", ""),
            Triple("Ramazan ayı, İslam takvimine göre 10. aydır.", "Yanlış", ""),
            Triple("Kur'an-ı Kerim 114 sureden oluşur.", "Doğru", ""),
            Triple("Zekât, İslam'ın 5 şartından biri değildir.", "Yanlış", ""),
            Triple("Hz. Muhammed (s.a.v.) son peygamberdir.", "Doğru", ""),
            Triple("Cuma namazı farz-ı ayındır.", "Doğru", ""),
            Triple("Oruç sadece Ramazan ayında tutulur.", "Yanlış", ""),
            Triple("Kıble yönü Mekke'deki Kabe'dir.", "Doğru", ""),
            Triple("İslam'ın 5 şartından biri seyahattir.", "Yanlış", ""),
            Triple("Abdestsiz namaz kılınabilir.", "Yanlış", "")
        )
        questions.forEach { (q, a, e) ->
            repository.insertQuestion("TRUE_FALSE", "EASY", q, a, "Doğru|Yanlış", e)
        }
    }

    private fun seedEasyQuestions() {
        val questions = listOf(
            listOf("İslam'ın kaç şartı vardır?", "5", "3|4|5|6"),
            listOf("Kur'an kaç sureden oluşur?", "114", "99|110|114|120"),
            listOf("Günde kaç vakit namaz kılınır?", "5", "3|4|5|6"),
            listOf("Hangi ay oruç tutulur?", "Ramazan", "Şaban|Muharrem|Ramazan|Zilhicce"),
            listOf("Kabe hangi şehirdedir?", "Mekke", "Medine|Mekke|Kudüs|Taif"),
            listOf("İmanın kaç şartı vardır?", "6", "4|5|6|7"),
            listOf("Fatiha suresi kaç ayettir?", "7", "5|6|7|8"),
            listOf("En uzun sure hangisidir?", "Bakara", "Al-i İmran|Nisa|Bakara|Maide"),
            listOf("Hac hangi ayda yapılır?", "Zilhicce", "Muharrem|Ramazan|Şevval|Zilhicce"),
            listOf("İslam'ın ilk emri nedir?", "Oku (İkra)", "Namaz kıl|Oruç tut|Oku (İkra)|Haccet")
        )
        questions.forEach { q ->
            repository.insertQuestion("MULTI_CHOICE_EASY", "EASY", q[0], q[1], q[2], "")
        }
    }

    private fun seedMediumQuestions() {
        val questions = listOf(
            listOf("Kadir gecesi hangi ayın kaçıncı gecesinde aranır?", "Ramazan'ın son 10 gecesi", "15. gece|21. gece|Ramazan'ın son 10 gecesi|Şaban'ın 15. gecesi"),
            listOf("Hz. Peygamber kaç yaşında peygamber oldu?", "40", "25|30|40|45"),
            listOf("Kuran-ı Kerim kaç yılda tamamlandı?", "23 yılda", "10|15|23 yılda|30 yılda"),
            listOf("İslam'da hicret hangi şehre yapıldı?", "Medine", "Mekke|Taif|Medine|Kudüs"),
            listOf("Tevhid ne anlama gelir?", "Allah'ın birliği", "Peygamberlik|Allah'ın birliği|Kıyamet|Cennet"),
            listOf("Hanifi mezhebi kime nispet edilir?", "İmam-ı Azam", "İmam Malik|İmam Şafi|İmam-ı Azam|İmam Ahmed"),
            listOf("Kuran'da en çok tekrar eden sure hangisidir?", "Fatiha", "Bakara|İhlas|Fatiha|Yasin"),
            listOf("Mirac olayı hangi şehirde başladı?", "Mekke", "Medine|Kudüs|Mekke|Taif"),
            listOf("İslam'da rüku ne demektir?", "Namazda eğilmek", "Secdeye gitmek|Namazda eğilmek|Oturmak|Kalkmak"),
            listOf("Zekat nisabı altın için kaç gramdır?", "80 gram", "40|60|80 gram|100")
        )
        questions.forEach { q ->
            repository.insertQuestion("MULTI_CHOICE_MEDIUM", "MEDIUM", q[0], q[1], q[2], "")
        }
    }

    private fun seedHardQuestions() {
        val questions = listOf(
            listOf("Ashab-ı Kehf kaç kişidir?", "7", "3|5|7|9"),
            listOf("Kur'an'da adı geçen tek kadın sahabiye kimdir?", "Meryem", "Hatice|Aişe|Meryem|Fatıma"),
            listOf("İlk inen ayet hangisidir?", "Alak suresi 1-5", "Fatiha 1|Bakara 1|Alak suresi 1-5|Müddessir 1"),
            listOf("Hudeybiye antlaşması hangi yılda yapıldı?", "628", "620|625|628|630"),
            listOf("Kur'an'da kaç defa 'Allah' ismi geçer?", "2699", "1000|1500|2699|3000"),
            listOf("İslam'da sehiv secdesi ne zaman yapılır?", "Namazda yanılınca", "Her namazdan sonra|Namazda yanılınca|Cuma günleri|Ramazanda"),
            listOf("Hangi sure 'Kur'an'ın kalbi' olarak bilinir?", "Yasin", "Bakara|Fatiha|Yasin|Kehf"),
            listOf("Mekke'nin fethi hangi yılda gerçekleşti?", "630", "620|625|630|635"),
            listOf("İslam'da taharet ne demektir?", "Temizlik", "İbadet|Temizlik|Dua|Oruç"),
            listOf("Kur'an'da en kısa sure hangisidir?", "Kevser", "İhlas|Felak|Kevser|Nas")
        )
        questions.forEach { q ->
            repository.insertQuestion("MULTI_CHOICE_HARD", "HARD", q[0], q[1], q[2], "")
        }
    }

    private fun seedSurahQuestions() {
        val questions = listOf(
            listOf("Bismillahirrahmanirrahim. Elhamdülillahi rabbil alemin...", "Fatiha", "Fatiha|Bakara|İhlas|Felak"),
            listOf("Kul hüvallahü ehad. Allahüssamed. Lem yelid ve lem yüled...", "İhlas", "Kevser|Nas|İhlas|Felak"),
            listOf("Kul euzü birabbilfelak. Min şerri ma halak...", "Felak", "Nas|Felak|İhlas|Kevser"),
            listOf("Kul euzü birabbin nas. Melikin nas. İlahin nas...", "Nas", "Felak|İhlas|Nas|Asr"),
            listOf("Vel asr. İnnel insane lefi husr...", "Asr", "Kevser|Asr|Fil|Kureyş"),
            listOf("İnna a'taynake'l kevser. Fesalli lirabbike venhar...", "Kevser", "Kevser|Kadr|Zilzal|Asr"),
            listOf("Elem tere keyfe fe'ale rabbüke biashabıl fil...", "Fil", "Kureyş|Fil|Hümeze|Maun"),
            listOf("Li'ilafi kureyş. İlafihim rıhletessitai vessayf...", "Kureyş", "Fil|Kureyş|Maun|Kevser"),
            listOf("İzâ câe nasrullahi vel feth...", "Nasr", "Nasr|Tebbet|İhlas|Kevser"),
            listOf("Tebbet yeda ebi lehebin ve tebb...", "Tebbet", "Nasr|Tebbet|İhlas|Felak")
        )
        questions.forEach { q ->
            repository.insertQuestion("SURAH_Q", "EASY", q[0], q[1], q[2], "")
        }
    }

    private fun seedSurahTest() {
        val questions = listOf(
            listOf("Fatiha suresi kaç ayettir?", "7", "5|6|7|8"),
            listOf("İhlas suresi kaç ayettir?", "4", "3|4|5|6"),
            listOf("Kevser suresi kaç ayettir?", "3", "2|3|4|5"),
            listOf("Yasin suresi kaç ayettir?", "83", "70|75|83|90"),
            listOf("Bakara suresi kaç ayettir?", "286", "200|250|286|300"),
            listOf("Nas suresi kaçıncı suredir?", "114", "112|113|114|115"),
            listOf("Felak suresi kaçıncı suredir?", "113", "111|112|113|114"),
            listOf("İhlas suresi kaçıncı suredir?", "112", "110|111|112|113"),
            listOf("Kadir suresi kaç ayettir?", "5", "3|4|5|6"),
            listOf("Zilzal suresi kaç ayettir?", "8", "6|7|8|9")
        )
        questions.forEach { q ->
            repository.insertQuestion("SURAH_TEST", "EASY", q[0], q[1], q[2], "")
        }
    }
}