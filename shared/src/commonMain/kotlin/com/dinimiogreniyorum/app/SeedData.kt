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
            listOf("Kur'an'da kaç sure vardır ve kaç ayettir?", "114 sure, 6236 ayet", "99 sure 5000 ayet|114 sure 6236 ayet|110 sure 6000 ayet|120 sure 7000 ayet"),
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