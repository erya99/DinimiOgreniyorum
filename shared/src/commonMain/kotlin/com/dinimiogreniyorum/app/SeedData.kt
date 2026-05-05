package com.dinimiogreniyorum.app

class SeedData(private val repository: QuestionRepository) {

    fun seedIfEmpty() {
        if (repository.getQuestionsByCategory("TRUE_FALSE").isEmpty()) seedTrueFalse()
        if (repository.getQuestionsByCategory("MULTI_CHOICE_EASY").isEmpty()) seedEasyQuestions()
        if (repository.getQuestionsByCategory("MULTI_CHOICE_MEDIUM").isEmpty()) seedMediumQuestions()
        if (repository.getQuestionsByCategory("MULTI_CHOICE_HARD").isEmpty()) seedHardQuestions()
        if (repository.getQuestionsByCategory("SURAH_Q").isEmpty()) seedSurahQuestions()
        if (repository.getQuestionsByCategory("SURAH_TEST").isEmpty()) seedSurahTest()
        if (repository.getQuestionsByCategory("MATCHING").isEmpty()) seedMatching()
        if (repository.getQuestionsByCategory("DAILY_POOL").isEmpty()) seedDailyPool()

        // GENEL TEST ALT KATEGORİLERİ KONTROLÜ
        if (repository.getQuestionsByCategory("GEN_TEMEL").isEmpty()) seedGeneralTests()
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
            listOf("Mirac olayı hangi şehirde başladı?", "Mekke", "Medine|Kudüs|Mekke|Taif"),
            listOf("İslam'da rüku ne demektir?", "Namazda eğilmek", "Secdeye gitmek|Namazda eğilmek|Oturmak|Kalkmak"),
            listOf("Zekat nisabı altın için kaç gramdır?", "80 gram", "40|60|80 gram|100"),
            listOf("Hz. Peygamber'in annesi kimdir?", "Amine", "Hatice|Amine|Fatıma|Meryem"),
            listOf("İslam'da niyet ne zaman yapılır?", "İbadetten önce", "İbadetten sonra|İbadet sırasında|İbadetten önce|Gerek yok"),
            listOf("Hangi namaz kılınmadan önce ezan okunmaz?", "Cenaze namazı", "Sabah|Öğle|Cenaze namazı|Cuma"),
            listOf("Kur'an ilk olarak hangi dilde indirilmiştir?", "Arapça", "Farsça|Türkçe|Arapça|İbranice"),
            listOf("İslam'da sünnet ne demektir?", "Hz. Peygamber'in yaşayış biçimi", "Farz ibadet|Hz. Peygamber'in yaşayış biçimi|Yasak olan şey|Dua"),
            listOf("Ramazan ayı kaç gün sürer?", "29 veya 30 gün", "28|29 veya 30 gün|31|45")
        )
        questions.forEach { q ->
            repository.insertQuestion("MULTI_CHOICE_MEDIUM", "MEDIUM", q[0], q[1], q[2], "")
        }
    }

    private fun seedHardQuestions() {
        val questions = listOf(
            listOf("Ashab-ı Kehf kaç kişidir?", "7", "3|5|7|9"),
            listOf("Kur'an'da adı geçen tek kadın peygamber annesi kimdir?", "Meryem", "Hatice|Aişe|Meryem|Fatıma"),
            listOf("İlk inen ayet hangisidir?", "Alak suresi 1-5", "Fatiha 1|Bakara 1|Alak suresi 1-5|Müddessir 1"),
            listOf("Hudeybiye antlaşması hangi yılda yapıldı?", "628", "620|625|628|630"),
            listOf("Kur'an'da kaç defa 'Allah' ismi geçer?", "2699", "1000|1500|2699|3000"),
            listOf("İslam'da sehiv secdesi ne zaman yapılır?", "Namazda yanılınca", "Her namazdan sonra|Namazda yanılınca|Cuma günleri|Ramazanda"),
            listOf("Hangi sure 'Kur'an'ın kalbi' olarak bilinir?", "Yasin", "Bakara|Fatiha|Yasin|Kehf"),
            listOf("Mekke'nin fethi hangi yılda gerçekleşti?", "630", "620|625|630|635"),
            listOf("İslam'da taharet ne demektir?", "Temizlik", "İbadet|Temizlik|Dua|Oruç"),
            listOf("Kur'an'da en kısa sure hangisidir?", "Kevser", "İhlas|Felak|Kevser|Nas"),
            listOf("Hz. Peygamber'in ilk vahyi aldığı mağara hangisidir?", "Hira", "Sevr|Hira|Uhud|Badr"),
            listOf("İslam'da 'İcma' ne demektir?", "Müctehitlerin görüş birliği", "Kıyas yöntemi|Müctehitlerin görüş birliği|Hadis ilmi|Kuran tefsiri"),
            listOf("Bedir Savaşı hangi yılda yapıldı?", "624", "620|622|624|626"),
            listOf("Kur'an'da kaç sure vardır ve kaç ayettir?", "114 sure 6236 ayet", "99 sure 5000 ayet|114 sure 6236 ayet|110 sure 6000 ayet|120 sure 7000 ayet"),
            listOf("İslam'ın yayıldığı ilk şehir hangisidir?", "Mekke", "Medine|Kudüs|Mekke|Taif")
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

    private fun seedDailyPool() {
        val questions = listOf(
            listOf("Allah'ın 99 ismi ne olarak bilinir?", "Esmaül Hüsna", "Sübhanallah|Esmaül Hüsna|Ayetel Kürsi|Kelime-i Tevhid"),
            listOf("İslam'da helal ne demektir?", "Dinen serbest olan", "Yasak olan|Dinen serbest olan|Farz olan|Sünnet olan"),
            listOf("Kur'an'da kaç peygamberin adı geçer?", "25", "10|15|25|30"),
            listOf("Hangi peygamber gemisini yaptı?", "Hz. Nuh", "Hz. İbrahim|Hz. Nuh|Hz. Musa|Hz. İsa"),
            listOf("İslam'da haram ne demektir?", "Kesinlikle yasak olan", "Serbest olan|Kesinlikle yasak olan|Tavsiye edilen|Zorunlu olan"),
            listOf("Kur'an hangi dilde indirilmiştir?", "Arapça", "Türkçe|Farsça|Arapça|İbranice"),
            listOf("Hz. Peygamber hangi şehirde doğdu?", "Mekke", "Medine|Kudüs|Mekke|Taif"),
            listOf("İslam'ın şartlarından biri olan kelime-i şehadet ne anlama gelir?", "Allah'tan başka ilah olmadığına tanıklık", "Namaz kılmak|Oruç tutmak|Allah'tan başka ilah olmadığına tanıklık|Hac yapmak"),
            listOf("Hangi sure Kur'an'ın başında yer alır?", "Fatiha", "Bakara|Fatiha|İhlas|Yasin"),
            listOf("İslam'da farz ne demektir?", "Yapılması zorunlu olan", "Yapılması yasak olan|Yapılması zorunlu olan|Tavsiye edilen|Serbest olan"),
            listOf("Hz. Muhammed'in babası kimdir?", "Abdullah", "Abdülmuttalip|Abdullah|Ebubekir|Ömer"),
            listOf("İslam'da müstehap ne demektir?", "Yapılması tavsiye edilen", "Zorunlu olan|Yasak olan|Yapılması tavsiye edilen|Serbest olan"),
            listOf("Mescid-i Haram hangi şehirdedir?", "Mekke", "Medine|Kudüs|Mekke|İstanbul"),
            listOf("Mescid-i Nebevi hangi şehirdedir?", "Medine", "Mekke|Medine|Kudüs|Taif"),
            listOf("İslam'da mekruh ne demektir?", "Yapılması hoş görülmeyen", "Zorunlu olan|Yapılması hoş görülmeyen|Yasak olan|Tavsiye edilen"),
            listOf("Hz. Peygamber kaç yıl yaşadı?", "63", "50|55|63|70"),
            listOf("Kur'an'da en çok adı geçen peygamber kimdir?", "Hz. Musa", "Hz. İsa|Hz. Musa|Hz. İbrahim|Hz. Muhammed"),
            listOf("İlk Müslüman olan kadın kimdir?", "Hz. Hatice", "Hz. Aişe|Hz. Fatıma|Hz. Hatice|Hz. Meryem"),
            listOf("İlk Müslüman olan erkek kimdir?", "Hz. Ebubekir", "Hz. Ömer|Hz. Ali|Hz. Ebubekir|Hz. Osman"),
            listOf("Kadir gecesi hangi aya denk gelir?", "Ramazan", "Şaban|Muharrem|Ramazan|Zilhicce"),
            listOf("İslam'da sela ne zaman okunur?", "Cenazelerde ve özel günlerde", "Her namazdan önce|Cenazelerde ve özel günlerde|Sadece Cumalarda|Bayramlarda"),
            listOf("Hz. İbrahim hangi ülkede doğdu?", "Irak (Ur şehri)", "Mısır|Suriye|Irak (Ur şehri)|Filistin"),
            listOf("Kur'an'da Meryem suresi hangi peygamberle ilgilidir?", "Hz. İsa", "Hz. Musa|Hz. Yahya|Hz. İsa|Hz. İbrahim"),
            listOf("İslam'da ictihad ne demektir?", "Dini konularda yorum yapmak", "Namaz kılmak|Dini konularda yorum yapmak|Oruç tutmak|Hac yapmak"),
            listOf("Ashab-ı Kiram ne demektir?", "Hz. Peygamber'in arkadaşları", "Peygamber soyundan gelenler|Hz. Peygamber'in arkadaşları|İslam alimleri|Kuran hafızları"),
            listOf("Hz. Ömer İslam'ı kaçıncı sırada kabul etti?", "40. kişi olarak", "5|10|40. kişi olarak|100"),
            listOf("İslam'da vakıf ne demektir?", "Hayır amaçlı mülk bağışı", "Namaz vakti|Hayır amaçlı mülk bağışı|Oruç niyeti|Dua şekli"),
            listOf("Kur'an'da kaç hizb vardır?", "60", "30|45|60|114"),
            listOf("İslam'da sadaka-i cariye ne demektir?", "Öldükten sonra da sevap getiren bağış", "Günlük sadaka|Öldükten sonra da sevap getiren bağış|Zekât miktarı|Fitre miktarı"),
            listOf("Hz. Peygamber'in hicret ettiği yıl hangi takvimin başlangıcıdır?", "Hicri takvim", "Miladi takvim|Rumi takvim|Hicri takvim|Güneş takvimi"),
            listOf("İslam'da istiğfar ne demektir?", "Allah'tan bağışlanma dilemek", "Şükretmek|Allah'tan bağışlanma dilemek|Dua etmek|Namaz kılmak"),
            listOf("Kur'an'da Yasin suresi kaçıncı suredir?", "36", "30|33|36|40"),
            listOf("İslam'da tevekkül ne demektir?", "Allah'a güvenip dayanmak", "Çok çalışmak|Allah'a güvenip dayanmak|Sabır göstermek|Dua etmek"),
            listOf("Hz. Ali kaçıncı halife oldu?", "4.", "1.|2.|3.|4."),
            listOf("Mekke'de ilk inşa edilen mescit hangisidir?", "Mescid-i Haram", "Mescid-i Nebevi|Mescid-i Aksa|Mescid-i Haram|Mescid-i Kuba"),
            listOf("İslam'da fitre ne zaman verilir?", "Ramazan Bayramı'ndan önce", "Kurban Bayramı'nda|Her ay|Ramazan Bayramı'ndan önce|Cuma günleri"),
            listOf("Kur'an'da Bakara suresi kaçıncı suredir?", "2.", "1.|2.|3.|4."),
            listOf("Hz. Peygamber'in veda hutbesi nerede okundu?", "Arafat'ta", "Mekke'de|Medine'de|Arafat'ta|Mina'da"),
            listOf("İslam'da kıyas ne demektir?", "Benzetme yoluyla hüküm çıkarmak", "Namaz vakti|Benzetme yoluyla hüküm çıkarmak|Kuran okumak|Dua etmek"),
            listOf("Hangi sure 'Amme' olarak bilinir?", "Nebe suresi", "Nas|Felak|Nebe suresi|Fatiha"),
            listOf("İslam'da hulul ne demektir?", "Allah'ın varlıklara girdiği inancı (reddedilen)", "Namaz kılmak|Allah'ın varlıklara girdiği inancı (reddedilen)|Oruç tutmak|Dua etmek"),
            listOf("Hz. Peygamber'in mührü ne üzerine yapılmıştı?", "Gümüş üzerine", "Altın|Demir|Gümüş üzerine|Bakır"),
            listOf("İslam'da tebliğ ne demektir?", "Dini yaymak ve anlatmak", "Namaz kılmak|Dini yaymak ve anlatmak|Oruç tutmak|Hac yapmak"),
            listOf("Kur'an'da İsra suresi hangi olayı anlatır?", "Miraç olayını", "Hicret|Bedir Savaşı|Miraç olayını|Uhud Savaşı"),
            listOf("İslam'da müftü ne demektir?", "Fetva veren din alimi", "Namaz kıldıran|Fetva veren din alimi|Kuran öğreten|Ezan okuyan"),
            listOf("Hz. Peygamber'in kaç tane erkek çocuğu oldu?", "3", "1|2|3|4"),
            listOf("İslam'da imam ne demektir?", "Namaz kıldıran kişi", "Kuran yazan|Namaz kıldıran kişi|Ezan okuyan|Din alimi"),
            listOf("Kur'an'da Kehf suresi okunması tavsiye edilen gün hangisidir?", "Cuma günü", "Pazartesi|Salı|Cuma günü|Cumartesi"),
            listOf("İslam'da müezzin ne demektir?", "Ezan okuyan kişi", "Namaz kıldıran|Ezan okuyan kişi|Hutbe okuyan|Kuran öğreten"),
            listOf("Hz. Peygamber'in en uzun süre yanında kalan sahabisi kimdir?", "Hz. Enes bin Malik", "Hz. Ebubekir|Hz. Ali|Hz. Enes bin Malik|Hz. Ömer"),
            listOf("İslam'da hac kaç günde tamamlanır?", "5-6 gün", "2|3|5-6 gün|10"),
            listOf("Kur'an'da Ayetel Kürsi hangi surede yer alır?", "Bakara", "Fatiha|Ali İmran|Bakara|Nisa"),
            listOf("İslam'da zekat kime verilmez?", "Zenginlere", "Fakirlere|Borçlulara|Zenginlere|Yolda kalanlara"),
            listOf("Hz. Peygamber'in annesi ne zaman vefat etti?", "Hz. Peygamber 6 yaşındayken", "Doğumdan önce|Hz. Peygamber 6 yaşındayken|Hz. Peygamber 10 yaşındayken|Hicret sırasında"),
            listOf("İslam'da Cuma namazı kaç rekattır?", "2 rekat farz", "2 rekat farz|4 rekat|6 rekat|8 rekat"),
            listOf("Kur'an'da hangi sure bismillah ile başlamaz?", "Tevbe suresi", "Fatiha|Bakara|Tevbe suresi|İhlas"),
            listOf("İslam'da itikaf ne demektir?", "Camide inzivaya çekilmek", "Oruç tutmak|Camide inzivaya çekilmek|Hac yapmak|Sadaka vermek"),
            listOf("Hz. Peygamber'in dedesi kimdir?", "Abdülmuttalip", "Abdullah|Ebubekir|Abdülmuttalip|Ebu Talip"),
            listOf("İslam'da arefe günü hangi günü ifade eder?", "Kurban Bayramı'ndan önceki gün", "Cuma|Ramazan'ın son günü|Kurban Bayramı'ndan önceki gün|Kandil gecesi"),
            listOf("Kur'an'da kaç cüz vardır?", "30", "10|20|30|40"),
            listOf("İslam'da namahrem ne demektir?", "Evlenilebilecek yabancı kişi", "Akraba|Evlenilebilecek yabancı kişi|Komşu|Arkadaş"),
            listOf("Hz. Peygamber'in ilk eşi kimdir?", "Hz. Hatice", "Hz. Aişe|Hz. Hatice|Hz. Fatıma|Hz. Zeynep"),
            listOf("İslam'da kunut duası hangi namazda okunur?", "Sabah namazında", "Öğle|Akşam|Sabah namazında|Yatsı"),
            listOf("Kur'an'da en son inen sure hangisidir?", "Nasr suresi", "Fatiha|İhlas|Nasr suresi|Bakara"),
            listOf("İslam'da hüsn-ü zan ne demektir?", "İyi niyet beslemek", "Kötü düşünmek|İyi niyet beslemek|Yardım etmek|Dua okumak"),
            listOf("Hz. Peygamber'in en sevdiği renk hangisiydi?", "Yeşil", "Kırmızı|Mavi|Yeşil|Sarı"),
            listOf("İslam'da sahur ne zaman yenir?", "Oruç öncesi imsak vakti", "Akşam|Öğle|Oruç öncesi imsak vakti|Gece yarısı"),
            listOf("Kur'an'da Hz. Yusuf'un kıssası hangi surede anlatılır?", "Yusuf suresi", "Kehf|Bakara|Yusuf suresi|Meryem"),
            listOf("İslam'da iftar ne zaman yapılır?", "Akşam ezanında", "Öğle ezanında|Akşam ezanında|Yatsı ezanında|Güneş doğduğunda"),
            listOf("Hz. Peygamber'in meşhur hadis kitabı hangisidir?", "Kütüb-ü Sitte", "Kütüb-ü Sitte|Mişkat|Riyazüssalihin|İhya"),
            listOf("İslam'da Regaib kandili hangi aya denk gelir?", "Recep ayı", "Şaban|Muharrem|Recep ayı|Zilhicce"),
            listOf("Kur'an'da Hz. Musa'nın kıssası en çok hangi surede geçer?", "Taha suresi", "Bakara|Kehf|Taha suresi|Kasas"),
            listOf("İslam'da cünüp ne demektir?", "Büyük hades halinde olan", "Abdestli olan|Büyük hades halinde olan|Oruçlu olan|Namazda olan"),
            listOf("Hz. Peygamber'in hicreti kaç yılında gerçekleşti?", "622", "610|615|622|630"),
            listOf("İslam'da istiare ne demektir?", "Ödünç almak", "Namaz kılmak|Ödünç almak|Dua etmek|Sadaka vermek"),
            listOf("Kur'an'da Rahman suresi kaçıncı suredir?", "55", "50|52|55|60"),
            listOf("İslam'da Berat kandili hangi aya denk gelir?", "Şaban ayı", "Recep|Muharrem|Şaban ayı|Zilkade"),
            listOf("Hz. Peygamber'in kaç kızı oldu?", "4", "1|2|3|4"),
            listOf("İslam'da ihram ne demektir?", "Hac için giyilen özel kıyafet", "Namaz elbisesi|Hac için giyilen özel kıyafet|Cenaze kefeni|Bayram kıyafeti"),
            listOf("Kur'an'da Mulk suresi kaçıncı suredir?", "67", "60|64|67|70"),
            listOf("İslam'da Miraç kandili hangi aya denk gelir?", "Recep ayı", "Şaban|Muharrem|Recep ayı|Zilhicce"),
            listOf("Hz. Peygamber'in torunu Hz. Hüseyin nerede şehit oldu?", "Kerbela'da", "Mekke'de|Medine'de|Kerbela'da|Şam'da"),
            listOf("İslam'da şükür ne demektir?", "Allah'a minnettar olmak", "Dua etmek|Allah'a minnettar olmak|Namaz kılmak|Oruç tutmak"),
            listOf("Kur'an'da Vakıa suresi ne ile ilgilidir?", "Kıyamet günü", "Cennet|Cehennem|Kıyamet günü|Dünya hayatı"),
            listOf("İslam'da mümin ne demektir?", "İman eden kişi", "Namaz kılan|İman eden kişi|Oruç tutan|Hac yapan"),
            listOf("Hz. Peygamber'in en meşhur mucizesi nedir?", "Kur'an-ı Kerim", "Denizi yarması|Ölü diriltmesi|Kur'an-ı Kerim|Ateşte yanmaması"),
            listOf("İslam'da hased ne demektir?", "Kıskançlık", "Sevgi|Kıskançlık|Merhamet|Saygı"),
            listOf("Kur'an'da Mülk suresi okunması tavsiye edilen vakit hangisidir?", "Her gece", "Sabah|Öğle|Her gece|Cuma"),
            listOf("İslam'da kibir ne demektir?", "Büyüklenme, gurur", "Alçakgönüllülük|Büyüklenme, gurur|Merhamet|Sabır"),
            listOf("Hz. Peygamber'in süt annesi kimdir?", "Halime Hatun", "Amine|Hatice|Halime Hatun|Fatıma"),
            listOf("İslam'da riya ne demektir?", "Gösteriş için ibadet etmek", "Samimi ibadet|Gösteriş için ibadet etmek|Gizli dua|Sadaka vermek"),
            listOf("Kur'an'da Hucurat suresi kaçıncı suredir?", "49", "45|47|49|52"),
            listOf("İslam'da hilafet ne demektir?", "İslam devlet başkanlığı", "Namaz imamlığı|İslam devlet başkanlığı|Din alimliği|Ezan okuma görevi"),
            listOf("Hz. Peygamber'in amcası Ebu Talip Müslüman oldu mu?", "Hayır, olmadı", "Evet oldu|Hayır, olmadı|Bilinmiyor|Ölüm döşeğinde oldu"),
            listOf("İslam'da gıybet ne demektir?", "Arkadan çekiştirmek", "Yalan söylemek|Arkadan çekiştirmek|Hırsızlık yapmak|İftira atmak"),
            listOf("Kur'an'da Fecr suresi kaçıncı suredir?", "89", "85|87|89|92"),
            listOf("İslam'da haset ile kıskançlık arasındaki fark nedir?", "Haset başkasının nimetinin yok olmasını istemektir", "Fark yoktur|Haset başkasının nimetinin yok olmasını istemektir|Kıskançlık daha kötüdür|İkisi de aynı şeydir"),
            listOf("Hz. Peygamber'in ilk vahyi aldığında kaç yaşındaydı?", "40", "25|30|35|40"),
            listOf("İslam'da marufu emretmek ne demektir?", "İyiliği emretmek", "Kötülüğü yasaklamak|İyiliği emretmek|Dua etmek|Namaz kılmak"),
            listOf("Kur'an'da Duha suresi kaçıncı suredir?", "93", "90|91|93|95"),
            listOf("İslam'da münker nedir?", "Dinen yasak olan şey", "İyi şey|Dinen yasak olan şey|Zorunlu olan|Tavsiye edilen"),
            listOf("Hz. Peygamber hangi gecede vefat etti?", "Pazartesi gecesi", "Cuma gecesi|Cumartesi gecesi|Pazartesi gecesi|Salı gecesi"),
            listOf("İslam'da huşu ne demektir?", "Namazda tam bir derin saygı ve teslimiyet", "Dua etmek|Namazda tam bir derin saygı ve teslimiyet|Oruç tutmak|Sadaka vermek"),
            listOf("Kur'an'da İnşirah suresi kaçıncı suredir?", "94", "91|92|94|96"),
            listOf("İslam'da şefaat ne demektir?", "Kıyamette aracılık etmek", "Dua etmek|Kıyamette aracılık etmek|Sadaka vermek|Oruç tutmak"),
            listOf("Hz. Peygamber'in vefatından sonra ilk halife kim oldu?", "Hz. Ebubekir", "Hz. Ömer|Hz. Ali|Hz. Ebubekir|Hz. Osman"),
            listOf("İslam'da kanaat ne demektir?", "Az ile yetinmek", "Çok çalışmak|Az ile yetinmek|Sadaka vermek|Dua etmek")
        )
        questions.forEach { q ->
            repository.insertQuestion("DAILY_POOL", "EASY", q[0], q[1], q[2], "")
        }
    }

    private fun seedGeneralTests() {
        val generalQuestions = mapOf(
            "GEN_TEMEL" to listOf(
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
            ),
            "GEN_KURAN" to listOf(
                listOf("Kur'an kaç cüzden oluşur?", "30", "20|25|30|40"),
                listOf("Kur'an'ın ilk suresi hangisidir?", "Fatiha", "Bakara|Fatiha|İhlas|Nas"),
                listOf("Kur'an'ın son suresi hangisidir?", "Nas", "Felak|İhlas|Nas|Kevser"),
                listOf("En kısa sure hangisidir?", "Kevser", "İhlas|Nas|Kevser|Asr"),
                listOf("İhlas suresi kaç ayettir?", "4", "3|4|5|6"),
                listOf("Kur'an kaç yılda tamamlandı?", "23", "10|15|23|30"),
                listOf("Kur'an hangi dilde indirilmiştir?", "Arapça", "Farsça|Türkçe|Arapça|İbranice"),
                listOf("Ayetel Kürsi hangi surede yer alır?", "Bakara", "Al-i İmran|Bakara|Nisa|Maide"),
                listOf("Kur'an'da kaç ayet vardır?", "6236", "5000|6000|6236|7000"),
                listOf("Kur'an'ın diğer adı nedir?", "Furkan", "Kitap|Furkan|Zebur|İncil")
            ),
            "GEN_NAMAZ" to listOf(
                listOf("Sabah namazı kaç rekattır?", "2", "2|3|4|5"),
                listOf("Öğle namazı kaç rekattır (farz)?", "4", "2|3|4|6"),
                listOf("İkindi namazı kaç rekattır (farz)?", "4", "2|3|4|6"),
                listOf("Akşam namazı kaç rekattır (farz)?", "3", "2|3|4|5"),
                listOf("Yatsı namazı kaç rekattır (farz)?", "4", "2|3|4|6"),
                listOf("Cuma namazı hangi vakitte kılınır?", "Öğle vakti", "Sabah vakti|Öğle vakti|İkindi vakti|Akşam vakti"),
                listOf("Namazda kıble hangi yöndür?", "Kabe yönü", "Doğu|Batı|Kabe yönü|Kuzey"),
                listOf("Namazın farzlarından biri hangisidir?", "Kıyam", "Niyet etmemek|Kıyam|Konuşmak|Yemek yemek"),
                listOf("Namaza başlarken ne söylenir?", "Allahu Ekber", "Bismillah|Elhamdülillah|Allahu Ekber|Sübhanallah"),
                listOf("Namazda oturuşta ne okunur?", "Ettehiyyatü", "Fatiha|Ettehiyyatü|İhlas|Felak")
            ),
            "GEN_ORUC" to listOf(
                listOf("Ramazan ayı kaç gündür?", "29 veya 30", "25|28|29 veya 30|31"),
                listOf("Orucu bozan şeylerden biri hangisidir?", "Yemek yemek", "Uyumak|Yürümek|Yemek yemek|Düşünmek"),
                listOf("Oruç ne zaman başlar?", "İmsak vakti", "Gün doğumu|Öğle|İmsak vakti|Akşam"),
                listOf("Oruç ne zaman bozulur?", "İftar vakti", "Öğle|Akşan ezanıyla|İftar vakti|Gece yarısı"),
                listOf("Ramazan ayında kaç gece Kadir Gecesi aranır?", "Son 10 gece", "İlk 10 gece|Orta 10 gece|Son 10 gece|Tüm ay"),
                listOf("Sahur ne demektir?", "İmsak öncesi yenen yemek", "İftar yemeği|Öğle yemeği|İmsak öncesi yenen yemek|Akşam yemeği"),
                listOf("Fitre ne zaman verilir?", "Ramazan Bayramı öncesi", "Kurban Bayramı|Ramazan başında|Ramazan Bayramı öncesi|Her Cuma"),
                listOf("Oruç hangi dini görevin şartıdır?", "İslam'ın şartı", "Namazın şartı|İslam'ın şartı|Haccın şartı|Zekatın şartı"),
                listOf("Hasta olan biri orucunu ne zaman kaza eder?", "İyileşince", "Hemen|Ramazanda|İyileşince|Hiç kazası yok"),
                listOf("Ramazan ayında oruç kaçıncı İslam şartıdır?", "3. şart", "1. şart|2. şart|3. şart|4. şart")
            ),
            "GEN_ZEKAT_HAC" to listOf(
                listOf("Zekat vermek kimin üzerine farzdır?", "Nisaba ulaşan Müslüman", "Herkes|Sadece zenginler|Nisaba ulaşan Müslüman|Sadece erkekler"),
                listOf("Hac kaçıncı İslam şartıdır?", "5. şart", "3. şart|4. şart|5. şart|6. şart"),
                listOf("Hac ibadeti hangi şehirlerde yapılır?", "Mekke ve Medine", "Mekke ve Kudüs|Mekke ve Medine|Medine ve Taif|Mekke ve Taif"),
                listOf("Hacda Arafat'ta durma ne zaman yapılır?", "9 Zilhicce", "7 Zilhicce|8 Zilhicce|9 Zilhicce|10 Zilhicce"),
                listOf("Zekat oranı ne kadardır?", "Kırkta bir (1/40)", "Onda bir|Yirmide bir|Kırkta bir (1/40)|Ellide bir"),
                listOf("Kurban Bayramı kaç günüdür?", "4 gün", "2 gün|3 gün|4 gün|5 gün"),
                listOf("Kurban hangi bayramda kesilir?", "Kurban Bayramı", "Ramazan Bayramı|Kurban Bayramı|Her iki bayramda|Cuma günleri"),
                listOf("Tavaf ne demektir?", "Kabe'yi 7 kez dolaşmak", "Safa-Merve arası yürümek|Kabe'yi 7 kez dolaşmak|Arafat'ta beklemek|Zemzem içmek"),
                listOf("Sa'y ne demektir?", "Safa ile Merve arasında yürümek", "Kabe'yi dolaşmak|Safa ile Merve arasında yürümek|Taş atmak|Kurban kesmek"),
                listOf("Zemzem nerededir?", "Mekke'de Kabe yakınında", "Medine'de|Kudüs'te|Mekke'de Kabe yakınında|Taif'te")
            ),
            "GEN_PEYGAMBERLER" to listOf(
                listOf("İslam'ın son peygamberi kimdir?", "Hz. Muhammed", "Hz. İsa|Hz. Musa|Hz. Muhammed|Hz. İbrahim"),
                listOf("İlk peygamber kimdir?", "Hz. Adem", "Hz. Nuh|Hz. İbrahim|Hz. Adem|Hz. İdris"),
                listOf("Kur'an hangi peygambere indirilmiştir?", "Hz. Muhammed", "Hz. İsa|Hz. Musa|Hz. Muhammed|Hz. Davud"),
                listOf("Tevrat hangi peygambere indirilmiştir?", "Hz. Musa", "Hz. İsa|Hz. Musa|Hz. Davud|Hz. İbrahim"),
                listOf("İncil hangi peygambere indirilmiştir?", "Hz. İsa", "Hz. İsa|Hz. Musa|Hz. Muhammed|Hz. Davud"),
                listOf("Zebur hangi peygambere indirilmiştir?", "Hz. Davud", "Hz. İsa|Hz. Musa|Hz. Davud|Hz. Süleyman"),
                listOf("Hz. Muhammed nerede doğmuştur?", "Mekke", "Medine|Mekke|Taif|Kudüs"),
                listOf("Hz. Muhammed kaç yaşında peygamber oldu?", "40", "25|30|40|45"),
                listOf("Hz. Muhammed'in babası kimdir?", "Hz. Abdullah", "Hz. Ömer|Hz. Abdullah|Hz. Ebubekir|Hz. Ali"),
                listOf("Hz. Muhammed'in annesi kimdir?", "Hz. Amine", "Hz. Hatice|Hz. Amine|Hz. Fatıma|Hz. Ayşe")
            ),
            "GEN_SIYER" to listOf(
                listOf("Hz. Muhammed kaç yılında doğdu?", "571", "550|560|571|580"),
                listOf("Hz. Muhammed'in ilk eşi kimdir?", "Hz. Hatice", "Hz. Ayşe|Hz. Hatice|Hz. Fatıma|Hz. Zeynep"),
                listOf("Hicret hangi şehirden hangi şehire yapıldı?", "Mekke'den Medine'ye", "Medine'den Mekke'ye|Mekke'den Medine'ye|Taif'ten Mekke'ye|Mekke'den Kudüs'e"),
                listOf("Miraç ne demektir?", "Hz. Muhammed'in göğe yükselmesi", "Hicret|Hz. Muhammed'in göğe yükselmesi|Kabe'nin inşası|İlk vahiy"),
                listOf("İlk vahiy hangi mağarada geldi?", "Hira Mağarası", "Sevr Mağarası|Hira Mağarası|Uhud Mağarası|Kuba Mağarası"),
                listOf("Hz. Muhammed kaç yaşında vefat etti?", "63", "55|60|63|70"),
                listOf("Hz. Muhammed'in kızı kimdir?", "Hz. Fatıma", "Hz. Ayşe|Hz. Fatıma|Hz. Hatice|Hz. Zeynep"),
                listOf("Hz. Muhammed'in damadı kimdir?", "Hz. Ali", "Hz. Ömer|Hz. Ebubekir|Hz. Ali|Hz. Osman"),
                listOf("Medine'deki ilk mescit hangisidir?", "Mescid-i Kuba", "Mescid-i Nebevi|Mescid-i Kuba|Mescid-i Haram|Mescid-i Aksa"),
                listOf("Hz. Muhammed'in lakabı nedir?", "El-Emin (Güvenilir)", "El-Aziz|El-Emin (Güvenilir)|El-Hakim|El-Kadir")
            ),
            "GEN_HALIFELER" to listOf(
                listOf("İlk halife kimdir?", "Hz. Ebubekir", "Hz. Ömer|Hz. Ebubekir|Hz. Osman|Hz. Ali"),
                listOf("İkinci halife kimdir?", "Hz. Ömer", "Hz. Ebubekir|Hz. Ömer|Hz. Osman|Hz. Ali"),
                listOf("Üçüncü halife kimdir?", "Hz. Osman", "Hz. Ömer|Hz. Ali|Hz. Osman|Hz. Talha"),
                listOf("Dördüncü halife kimdir?", "Hz. Ali", "Hz. Hasan|Hz. Hüseyin|Hz. Ali|Hz. Zübeyr"),
                listOf("Dört halifeye ne denir?", "Hulefa-i Raşidin", "Sahabe|Hulefa-i Raşidin|Tabiin|Evliya")
            ),
            "GEN_IMAN" to listOf(
                listOf("İmanın şartlarından biri hangisidir?", "Allah'a iman", "Namaz kılmak|Allah'a iman|Oruç tutmak|Zekat vermek"),
                listOf("Meleklere iman etmek kaçıncı iman şartıdır?", "2. şart", "1. şart|2. şart|3. şart|4. şart"),
                listOf("Kader ve kazaya iman etmek kaçıncı iman şartıdır?", "6. şart", "3. şart|4. şart|5. şart|6. şart"),
                listOf("Ahirete iman etmek kaçıncı iman şartıdır?", "5. şart", "3. şart|4. şart|5. şart|6. şart"),
                listOf("Peygamberlere iman etmek kaçıncı iman şartıdır?", "3. şart", "1. şart|2. şart|3. şart|4. şart"),
                listOf("Kitaplara iman etmek kaçıncı iman şartıdır?", "4. şart", "2. şart|3. şart|4. şart|5. şart"),
                listOf("İslam'da Allah'ın birliğine ne denir?", "Tevhid", "Şirk|Tevhid|Küfür|Nifak"),
                listOf("Allah'a ortak koşmaya ne denir?", "Şirk", "Küfür|Nifak|Şirk|Bid'at"),
                listOf("İslam'da Allah'ın sıfatlarından biri hangisidir?", "Hay (Diri olan)", "Ölümlü|Hay (Diri olan)|Yorulan|Uyuyan"),
                listOf("Allah'ın varlığına ve birliğine şahitlik etmeye ne denir?", "Kelime-i Şehadet", "Besmele|Fatiha|Kelime-i Şehadet|Salavat")
            ),
            "GEN_MELEKLER" to listOf(
                listOf("Vahiy meleği hangisidir?", "Cebrail", "Mikail|Cebrail|İsrafil|Azrail"),
                listOf("Yağmur ve rızıktan sorumlu melek hangisidir?", "Mikail", "Cebrail|Mikail|İsrafil|Azrail"),
                listOf("Ölüm meleği hangisidir?", "Azrail", "Cebrail|Mikail|İsrafil|Azrail"),
                listOf("Sura üfleyecek melek hangisidir?", "İsrafil", "Cebrail|Mikail|İsrafil|Azrail"),
                listOf("İnsanların amellerini yazan melekler hangisidir?", "Kiramen Katibin", "Münker ve Nekir|Kiramen Katibin|Hafaza melekleri|Hamele-i Arş"),
                listOf("Kabir sorgusunu yapan melekler kimlerdir?", "Münker ve Nekir", "Kiramen Katibin|Münker ve Nekir|Cebrail ve Mikail|Hafaza melekleri"),
                listOf("Cennet kapılarını koruyan melek kimdir?", "Rıdvan", "Cebrail|Azrail|Rıdvan|Malik"),
                listOf("Cehennem bekçisi olan melek kimdir?", "Malik", "Cebrail|Rıdvan|Malik|Mikail")
            ),
            "GEN_AHLAK_IBADET" to listOf(
                listOf("Bismillah ne anlama gelir?", "Allah'ın adıyla", "Allah büyüktür|Allah'ın adıyla|Allah'a hamdolsun|Allah'tan başka ilah yoktur"),
                listOf("Elhamdülillah ne anlama gelir?", "Hamd Allah'a mahsustur", "Allah büyüktür|Allah'ın adıyla|Hamd Allah'a mahsustur|Allah dilerse"),
                listOf("Allahu Ekber ne anlama gelir?", "Allah en büyüktür", "Allah birdir|Allah'ın adıyla|Allah en büyüktür|Allah yaratıcıdır"),
                listOf("Sübhanallah ne anlama gelir?", "Allah her türlü eksiklikten münezzehtir", "Allah büyüktür|Allah'a hamdolsun|Allah her türlü eksiklikten münezzehtir|Allah'a şükürler"),
                listOf("İnşallah ne anlama gelir?", "Allah dilerse", "Allah büyüktür|Allah dilerse|Allah'a sığınırım|Allah'a hamdolsun"),
                listOf("Maşallah ne anlama gelir?", "Allah ne dilemiş (güzel)", "Allah büyüktür|Allah dilerse|Allah ne dilemiş (güzel)|Allah korusun"),
                listOf("Abdest almak hangi ibadetin ön şartıdır?", "Namaz", "Oruç|Hac|Namaz|Zekat"),
                listOf("Gusül abdesti ne zaman gerekir?", "Cünüp olunca", "Her namazdan önce|Uyku sonrası|Cünüp olunca|Her gün"),
                listOf("Teyemmüm ne demektir?", "Toprakla abdest almak", "Suyla abdest|Toprakla abdest almak|Dua etmek|Sadaka vermek"),
                listOf("Kıble ne demektir?", "Namaz kılınırken yönelinen yön", "Namaz vakti|Namaz kılınırken yönelinen yön|Namaz yeri|Namaz duası")
            ),
            "GEN_BAYRAMLAR" to listOf(
                listOf("Ramazan Bayramı kaç gündür?", "3 gün", "2 gün|3 gün|4 gün|5 gün"),
                listOf("Kurban Bayramı ne zaman başlar?", "10 Zilhicce", "8 Zilhicce|9 Zilhicce|10 Zilhicce|11 Zilhicce"),
                listOf("Kadir Gecesi hangi ayda aranır?", "Ramazan", "Şaban|Muharrem|Ramazan|Zilhicce"),
                listOf("Cuma namazı haftanın hangi günü kılınır?", "Cuma", "Çarşamba|Perşembe|Cuma|Cumartesi"),
                listOf("Müslümanların bayramları kaç tanedir?", "2", "1|2|3|4"),
                listOf("Mevlid Kandili neyi kutlar?", "Hz. Muhammed'in doğumunu", "Hz. Muhammed'in vefatını|Hz. Muhammed'in doğumunu|İlk vahyi|Hicret'i"),
                listOf("Regaib Kandili hangi ayda kutlanır?", "Recep", "Şaban|Recep|Ramazan|Zilhicce"),
                listOf("Mirac Kandili hangi olayı kutlar?", "Hz. Muhammed'in miracını", "Hicret|Hz. Muhammed'in doğumunu|Hz. Muhammed'in miracını|İlk vahyi"),
                listOf("Berat Kandili hangi ayda kutlanır?", "Şaban", "Recep|Şaban|Ramazan|Muharrem"),
                listOf("Aşure Günü hangi ayın 10. günüdür?", "Muharrem", "Recep|Şaban|Ramazan|Muharrem")
            ),
            "GEN_AHIRET" to listOf(
                listOf("İslam'da öldükten sonra gidilen iyi yer neresidir?", "Cennet", "Araf|Cehennem|Cennet|Berzah"),
                listOf("İslam'da günahkarların gideceği yer neresidir?", "Cehennem", "Araf|Berzah|Cennet|Cehennem"),
                listOf("Kıyamet gününün diğer adı nedir?", "Ahiret", "Berzah|Araf|Ahiret|Mizan"),
                listOf("Ölümden sonra dirilişe ne denir?", "Haşr (Ba's)", "Kıyamet|Hesap|Haşr (Ba's)|Mizan"),
                listOf("Amel defteri nedir?", "İnsanların yaptıklarının yazıldığı kayıt", "Kur'an|Hadis|İnsanların yaptıklarının yazıldığı kayıt|Dua kitabı"),
                listOf("Sırat köprüsü nedir?", "Cehennemi geçen köprü", "Cennete giriş kapısı|Cehennemi geçen köprü|Hesap yeri|Bekleme alanı"),
                listOf("Cennetin kaç kapısı vardır?", "8", "4|6|7|8"),
                listOf("Cehennemin kaç kapısı vardır?", "7", "4|5|6|7"),
                listOf("Araf ne demektir?", "Cennet ve cehennem arası yer", "Cennet|Cehennem|Cennet ve cehennem arası yer|Dünya"),
                listOf("Hesap günü ne zaman gerçekleşir?", "Kıyametten sonra", "Ölümden hemen sonra|Kıyametten sonra|Mezarda|Dünyada")
            ),
            "GEN_SURELER" to listOf(
                listOf("Yasin suresi kaçıncı suredir?", "36", "32|34|36|38"),
                listOf("Mülk suresi kaçıncı suredir?", "67", "65|66|67|68"),
                listOf("Nebe suresi kaçıncı suredir?", "78", "75|76|78|80"),
                listOf("Fatiha suresinde geçen 'sırat' ne anlama gelir?", "Yol", "Cennet|Nimet|Yol|Doğruluk"),
                listOf("İhlas suresi neyi anlatır?", "Allah'ın birliğini", "Kıyameti|Allah'ın birliğini|Ahireti|Peygamberi"),
                listOf("Felak suresi neyi anlatır?", "Şerden Allah'a sığınmayı", "Cenneti|Allah'ın sıfatlarını|Şerden Allah'a sığınmayı|Peygamberi"),
                listOf("Nas suresi kime hitap eder?", "İnsanlara", "Meleklere|Cinlere|İnsanlara|Peygambere"),
                listOf("Asr suresi kaç ayettir?", "3", "2|3|4|5"),
                listOf("Fil suresi hangi olayı anlatır?", "Kabe'ye fil ordusu saldırısını", "Tufanı|Kabe'ye fil ordusu saldırısını|Hicret'i|Miraci"),
                listOf("Kureyş suresi hangi kabileyi anlatır?", "Kureyş kabilesini", "Evs kabilesini|Hazrec kabilesini|Kureyş kabilesini|Ensar kabilesini")
            ),
            "GEN_TERIMLER" to listOf(
                listOf("'Salat' ne demektir?", "Namaz", "Oruç|Hac|Namaz|Zekat"),
                listOf("'Savm' ne demektir?", "Oruç", "Namaz|Oruç|Hac|Zekat"),
                listOf("'Hajj' ne demektir?", "Hac", "Namaz|Oruç|Hac|Zekat"),
                listOf("'Zakat' ne demektir?", "Zekat (arınma/bağış)", "Namaz|Oruç|Hac|Zekat (arınma/bağış)"),
                listOf("'Şehadet' ne demektir?", "Tanıklık/şahitlik", "Namaz|Oruç|Tanıklık/şahitlik|Dua"),
                listOf("'Dua' ne demektir?", "Allah'a yalvarmak/istemek", "Namaz kılmak|Allah'a yalvarmak/istemek|Oruç tutmak|Zekat vermek"),
                listOf("'Farz' ne demektir?", "Zorunlu/yapılması gereken", "Zorunlu/yapılması gereken|Yasak|İsteğe bağlı|Tavsiye edilen"),
                listOf("'Haram' ne demektir?", "Kesinlikle yasak olan", "Zorunlu|Tavsiye edilen|Kesinlikle yasak olan|Mekruh"),
                listOf("'Helal' ne demektir?", "Dinen serbest olan", "Yasak olan|Dinen serbest olan|Zorunlu olan|Tavsiye edilen"),
                listOf("'Sünnet' ne demektir?", "Hz. Muhammed'in yolu/uygulaması", "Kur'an ayeti|Hz. Muhammed'in yolu/uygulaması|Farz ibadet|Haram davranış")
            ),
            "GEN_TARIH" to listOf(
                listOf("İslam ne zaman ortaya çıktı?", "610 yılında", "500|570|610|650"),
                listOf("Hicret hangi yılda gerçekleşti?", "622", "610|615|622|630"),
                listOf("Mekke'nin fethi hangi yılda oldu?", "630", "620|625|630|635"),
                listOf("Hz. Muhammed hangi yılda vefat etti?", "632", "628|630|632|635"),
                listOf("İslam takvimi hangi olaydan başlar?", "Hicret'ten", "Hz. Muhammed'in doğumundan|İlk vahiyden|Hicret'ten|Mekke fethinden"),
                listOf("İslam takviminde yıl ne ile adlandırılır?", "Hicri yıl", "Miladi yıl|Hicri yıl|Rumi yıl|Kamerî yıl"),
                listOf("Hz. Muhammed'in doğduğu yer neresidir?", "Mekke", "Medine|Mekke|Taif|Kudüs"),
                listOf("İlk Müslümanlardan biri olan Hz. Hatice kimdir?", "Hz. Muhammed'in eşi", "Hz. Muhammed'in annesi|Hz. Muhammed'in eşi|Hz. Muhammed'in kızı|Hz. Muhammed'in kız kardeşi"),
                listOf("Bedir Savaşı hangi yılda oldu?", "624", "620|622|624|626"),
                listOf("Uhud Savaşı hangi yılda oldu?", "625", "622|623|625|627")
            ),
            "GEN_DEGERLER" to listOf(
                listOf("İslam'da yalan söylemek nasıl değerlendirilir?", "Günah", "Sevap|Günah|Mubah|Tavsiye edilen"),
                listOf("İslam'da anne-babaya saygı nasıl değerlendirilir?", "Farzdır", "İsteğe bağlıdır|Farzdır|Sünnettir|Tavsiye edilmez"),
                listOf("İslam'da komşu hakkına riayet etmek nasıldır?", "Önemlidir", "Önemsizdir|Önemlidir|İsteğe bağlıdır|Yasaklanmıştır"),
                listOf("İslam'da hırsızlık nasıl değerlendirilir?", "Haramdır", "Helaldir|Mekruhtur|Haramdır|Müstehaptır"),
                listOf("İslam'da sadaka vermek nasıldır?", "Sevaptır", "Günahtır|Mekruhtur|Sevaptır|Farzdır"),
                listOf("İslam'da doğruluk ve dürüstlük nasıl değerlendirilir?", "Erdemlidir", "Zayıflıktır|Erdemlidir|Zararlıdır|Önemsizdir"),
                listOf("İslam'da emanete ihanet etmek nasıldır?", "Haramdır", "Helaldir|Mekruhtur|Haramdır|Sünnettir"),
                listOf("İslam'da büyüklere saygı nasıldır?", "Önemlidir", "Önemsizdir|Önemlidir|Yasaklanmıştır|Zayıflıktır"),
                listOf("İslam'da çevre temizliği nasıl değerlendirilir?", "İmanın gereğidir", "Önemsizdir|İmanın gereğidir|Yasaklanmıştır|İsteğe bağlıdır"),
                listOf("İslam'da selamlama nasıldır?", "Sünnettir", "Haramdır|Farzdır|Sünnettir|Mekruhtur")
            ),
            "GEN_YAYILIS" to listOf(
                listOf("İslam'ın ilk yayıldığı bölge neresidir?", "Arabistan Yarımadası", "Anadolu|Mısır|Arabistan Yarımadası|İran"),
                listOf("İlk İslam devleti nerede kuruldu?", "Medine", "Mekke|Medine|Şam|Bağdat"),
                listOf("İslam'ın yayılmasında en büyük etken nedir?", "Tebliğ ve örnek yaşam", "Savaş|Ticaret|Tebliğ ve örnek yaşam|Göç"),
                listOf("Ensar ne demektir?", "Yardımcılar (Medineli Müslümanlar)", "Mekke'den göç edenler|Yardımcılar (Medineli Müslümanlar)|İlk Müslümanlar|Sahabeler"),
                listOf("Muhacir ne demektir?", "Hicret eden (Mekke'den göç eden) Müslümanlar", "Yardımcılar|Hicret eden (Mekke'den göç eden) Müslümanlar|İlk Müslümanlar|Peygamberin arkadaşları")
            ),
            "GEN_EK_KURAN" to listOf(
                listOf("Namazda kaç defa secde edilir (bir rekatta)?", "2", "1|2|3|4"),
                listOf("Cuma günü hangi sure okunması tavsiye edilir?", "Kehf suresi", "Yasin suresi|Kehf suresi|Mülk suresi|Bakara suresi"),
                listOf("Teravih namazı hangi ayda kılınır?", "Ramazan", "Şaban|Recep|Ramazan|Zilhicce"),
                listOf("Vitir namazı hangi vakit kılınır?", "Yatsıdan sonra", "Sabahtan önce|Öğleden sonra|Yatsıdan sonra|İkindiden sonra"),
                listOf("Cenaze namazında kaç tekbir alınır?", "4", "2|3|4|5"),
                listOf("Kıraet ne demektir?", "Kur'an okumak", "Namaz kılmak|Oruç tutmak|Kur'an okumak|Dua etmek"),
                listOf("Ruku nedir?", "Namazda eğilmek", "Namazda oturmak|Namazda eğilmek|Namazda kalkmak|Namazda yürümek"),
                listOf("Secde nedir?", "Alnı yere koymak", "Eğilmek|Alnı yere koymak|Ayağa kalkmak|Oturmak"),
                listOf("Kıyam ne demektir?", "Namazda ayakta durmak", "Namazda oturmak|Namazda eğilmek|Namazda ayakta durmak|Namazda secde etmek"),
                listOf("Ka'de ne demektir?", "Namazda oturmak", "Namazda ayakta durmak|Namazda oturmak|Namazda secde etmek|Namazda eğilmek")
            ),
            "GEN_EK_KAVRAMLAR" to listOf(
                listOf("Tevbe ne demektir?", "Günahtan dönmek/pişman olmak", "Dua etmek|Namaz kılmak|Günahtan dönmek/pişman olmak|Oruç tutmak"),
                listOf("İstiğfar ne demektir?", "Allah'tan bağışlanma dilemek", "Şükretmek|Allah'tan bağışlanma dilemek|Namaz kılmak|Dua etmek"),
                listOf("Şükür ne demektir?", "Nimetler için Allah'a minnettar olmak", "Namaz kılmak|Nimetler için Allah'a minnettar olmak|Oruç tutmak|Zekat vermek"),
                listOf("Tevekkül ne demektir?", "Allah'a güvenmek ve dayanmak", "Tembellik etmek|Allah'a güvenmek ve dayanmak|Dua etmemek|Çalışmamak"),
                listOf("Sabır ne demektir?", "Zorluklara dayanmak", "Acele etmek|Zorluklara dayanmak|Şikayet etmek|Vazgeçmek"),
                listOf("Hidayet ne demektir?", "Doğru yolu bulmak", "Yanlış yola gitmek|Doğru yolu bulmak|Günah işlemek|Allah'ı inkâr etmek"),
                listOf("Tövbe-i nasuh ne demektir?", "Samimi ve kararlı tövbe", "Günaha devam etmek|Samimi ve kararlı tövbe|Kısa süreli pişmanlık|Başkası için dua etmek"),
                listOf("Rıza ne demektir?", "Allah'ın hükmüne razı olmak", "İsyan etmek|Allah'ın hükmüne razı olmak|Şikayet etmek|Nankör olmak"),
                listOf("Huşu ne demektir?", "İbadette derin saygı ve dikkat", "İbadeti terk etmek|İbadette derin saygı ve dikkat|İbadeti aceleye getirmek|İbadetten kaçmak"),
                listOf("Takva ne demektir?", "Allah'tan korkarak günahtan kaçınmak", "Allah'ı sevmemek|Allah'tan korkarak günahtan kaçınmak|Dünyayı sevmek|İbadeti terk etmek")
            ),
            "GEN_EK_TAMAM" to listOf(
                listOf("Hz. İbrahim'in ateşe atılmasına rağmen yanmaması hangi olayı anlatır?", "Allah'ın onu korumasını", "Mucize|Allah'ın onu korumasını|Sihir|Tesadüf"),
                listOf("Hz. Musa'nın denizi yarması nasıl gerçekleşti?", "Allah'ın mucizesiyle", "Sihirle|Allah'ın mucizesiyle|Kendi gücüyle|Meleklerin yardımıyla"),
                listOf("Hz. Nuh'un gemisi hangi dağa oturmuştur?", "Cudi Dağı", "Ağrı Dağı|Cudi Dağı|Everest|Sinai Dağı"),
                listOf("İslam'da namazın önemi nedir?", "Dinin direğidir", "İsteğe bağlıdır|Dinin direğidir|Sadece Cuma kılınır|Yaşlılara farzdır"),
                listOf("Hangi sure 'Kur'an'ın kalbi' olarak bilinir?", "Yasin suresi", "Fatiha suresi|Bakara suresi|Yasin suresi|Mülk suresi"),
                listOf("Kelime-i Tevhid nedir?", "Lailahe illallah", "Bismillah|Elhamdülillah|Lailahe illallah|Allahu Ekber"),
                listOf("İslam'da en büyük günah nedir?", "Şirk (Allah'a ortak koşmak)", "Yalan söylemek|Şirk (Allah'a ortak koşmak)|Hırsızlık|Zina"),
                listOf("Sahabe ne demektir?", "Hz. Muhammed'i gören ve ona inanan kişi", "Hz. Muhammed'in akrabaları|Hz. Muhammed'i gören ve ona inanan kişi|İlk halifelar|Kur'an hafızları"),
                listOf("Hadis ne demektir?", "Hz. Muhammed'in söz ve davranışları", "Kur'an ayetleri|Hz. Muhammed'in söz ve davranışları|Dini kitaplar|Alim görüşleri"),
                listOf("Fıkıh ne demektir?", "İslam hukuku", "Kur'an tefsiri|Hadis bilimi|İslam hukuku|Kelam ilmi"),
                listOf("Tefsir ne demektir?", "Kur'an'ı açıklama ve yorumlama", "Hadis ezberlemek|Kur'an'ı açıklama ve yorumlama|Namaz kılmak|Dua etmek")
            )
        )

        // Verileri veritabanına ekle
        generalQuestions.forEach { (categoryId, questions) ->
            questions.forEach { q ->
                repository.insertQuestion(categoryId, "EASY", q[0], q[1], q[2], "")
            }
        }
    }

    private fun seedMatching() {
        val sets = listOf(
            listOf(
                "Sure adlarını ayet sayılarıyla eşleştir",
                "Fatiha|Bakara|İhlas|Kevser",
                "7|286|4|3"
            ),
            listOf(
                "Sure adlarını ayet sayılarıyla eşleştir",
                "Yasin|Nas|Felak|Asr",
                "83|6|5|3"
            ),
            listOf(
                "Peygamberleri mucizeleriyle eşleştir",
                "Hz. Musa|Hz. İsa|Hz. Süleyman|Hz. İbrahim",
                "Denizi yarma|Ölüleri diriltme|Kuşlarla konuşma|Ateşte yanmama"
            ),
            listOf(
                "Peygamberleri mucizeleriyle eşleştir",
                "Hz. Davud|Hz. Salih|Hz. Yunus|Hz. Nuh",
                "Demir işleme|Deve mucizesi|Balığın karnında kalma|Tufan gemisi"
            ),
            listOf(
                "İbadetleri tanımlarıyla eşleştir",
                "Namaz|Oruç|Zekât|Hac",
                "Günde 5 vakit kılınan ibadet|Yemek içmekten uzak durmak|Malın belirli kısmını vermek|Kabe'yi ziyaret etmek"
            ),
            listOf(
                "Kavramları tanımlarıyla eşleştir",
                "Abdest|Gusül|Teyemmüm|Kıble",
                "Namaz öncesi uzuv yıkama|Tüm bedeni yıkama|Toprakla temizlenme|Namaz yönü"
            ),
            listOf(
                "Arapça kavramları Türkçe anlamlarıyla eşleştir",
                "Tevhid|Takva|Sabır|Şükür",
                "Allah'ın birliği|Allah'tan korkup sakınma|Dayanma ve katlanma|Minnettarlık"
            ),
            listOf(
                "Arapça kavramları Türkçe anlamlarıyla eşleştir",
                "İhlas|Tövbe|Dua|Zikir",
                "Samimilik|Günahtan dönmek|Allah'a yalvarmak|Allah'ı anmak"
            )
        )
        sets.forEach { set ->
            repository.insertQuestion("MATCHING", "EASY", set[0], set[2], set[1], "")
        }
    }
}