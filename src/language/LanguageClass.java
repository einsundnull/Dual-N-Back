package language;

import java.util.ArrayList;

public class LanguageClass {

	public static String visualMatch = "S";
	public static String audioMatch = "L";

	static String[] en = {

			"Create User", // 0
			"Select User", "Start Training", "Show Results", "Store Results", "User: ", // 5
			"User", "User already exists! Overwrite? All data will be lost!", "Level: ", "Duration In Minutes: ",
			"Trials: ", // 10
			"Visual", "Auditory", "Long Time Training", "Change user without saving?", "Yes", // 15
			"No", "Overwrite Data?", "Save", "Cancel", "EXIT", // 20
			"PAUSED", "PRESS ESC TO CONTINUE", "Your rate of right answers in the visual task is ",
			"Your rate of right answers in the audio task is ", " - back", // 25
			"Create", "Test Volume", "EN_", "BACK", "STOP", // 30
			"Read Manual", "", "",
			// "Unfortunately this translation has not yet been checked",
			// "Unfortunately, only English audio is currently available for
			// this language",
			"Average performance per day", "Average performance per session", "Show all runs",
			"PRESS THE SPACE BUTTON TO BEGIN", // 37
			"N-BACK version", "Highest-level reached a day", "Highest-level reached a session",
			"Hover over a point to show the date of the session",
			"Do you want to exit the program without saving the data?",
			"©2020 Notorein. All rights reserved! \n" // 43
					+ "Commercial distribution of this program is strictly prohibited! \n"
					+ "The free distribution of this program is allowed! \n"// 43
					+ "You use this program at your own risk!", // 43
			"I have read and understood the conditions!", // 44
			"Accept conditions!", // 45
			"Refuse conditions!", "Sound and Position", "Sound and Color", "PRESS THE SPACE BUTTON TO FINISH",
			"Delete that user? All data will be lost!", // 50
			"Exit without saving? Recent achievements will be lost!", "Please choose an user!",
			"Press   " + visualMatch + "   to confirm visual matches \n" + "Press   " + audioMatch
					+ "   to confirm audio matches",
			"Please save the results before creating a new user!", "Donate", "The Bitcoin address is:", "Back",
			"Copy the address", "The address has been copied! \n" + "You can paste it now!",
			"The update domain is under maintenance. \n" + "Please write an e-mail to notorein@gmail.com to \n"
					+ "get an updated version! \n" + "Your current version is 2.1.0 \n"
					+ "Last changes on the program occurred in 2020/11/21",
			"UPDATE", "OK", "Please contact notorein@gmail.com to make sure\n"
					+ "that the following address is up to date before donating!",
			"Combined Results"

	};

	static String[] es = { "Crear usuario", "Seleccionar usuario", "Empezar a entrenar", "Mostrar resultados",
			"Guardar resultados", "Usuario: ", "Usuario",
			"¡El usuario ya existe! ¿Sobrescribir? ¡Se perderán todas las cadenas de idioma!", "Nivel: ",
			"Duración en minutos: ", "Ensayos:", "Visual", "Auditivo", "Entrenamiento de mucho tiempo",
			"¿Cambiar de usuario sin almacenar datos?", "Si", "No", "¿Sobrescribir datos?", "Salvar", "Cancelar",
			"SALIDA", "EN PAUSA", "PRESIONE ESC PARA CONTINUAR",
			"Su índice de respuestas correctas en la tarea visual es",
			"Su índice de respuestas correctas en la tarea auditiva es", " - espalda", "Crear", "Volumen de prueba",
			"ES_", "ESPALDA", "DETENER", "Leer manual", "", "Lamentablemente, esta traducción aún no se ha verificado",
			// "Desafortunadamente, solo el audio en inglés está disponible
			// actualmente para este idioma",
			"Rendimiento medio por día.", "Rendimiento medio por sesión", "Mostrar todas las carreras",
			"PRESIONE EL BOTÓN DE ESPACIO PARA COMENZAR", // 37
			"Versión N-BACK", "Nivel más alto alcanzado un día", "El nivel más alto alcanzó una sesión",
			"Coloca el cursor sobre un punto para mostrar la fecha de la sesión",
			"¿Quieres salir del programa sin guardar los datos?",
			"©2020 Notorein \n" // 43
					+ "¡La distribución comercial de este programa está estrictamente prohibida! \n"
					+ "¡Se permite la distribución gratuita de este programa! \n" // 43
					+ "¡Usa este programa bajo su propio riesgo!", // 43
			"¡He leído y entendido las condiciones!", // 44
			"¡Acepta condiciones!", // 45
			"¡Rechace las condiciones!", "Sonido y posición", "Sonido y color",
			"PRESIONA EL BOTÓN ESPACIADOR PARA TERMINAR", // 49
			"¿Quieres eliminar ese usuario? ¡Todos los datos se perderán!",
			"¿Salir sin guardar? ¡Los logros recientes se perderán!", "Por favor elige una usuaria / un usuario!",
			"Presiona   " + visualMatch + "   para confirmar las coincidencias visuales \n" + "Presione   " + audioMatch
					+ "   para confirmar las coincidencias de audio",
			"¡Guarde los resultados antes de crear un nuevo usuario!", "Donar", "La dirección de Bitcoin es:",
			"Espalda", "Copiar la dirección", "¡La dirección ha sido copiada! \n" + "¡Puedes pegarla ahora!",
			"El dominio de actualización está en mantenimiento. \n"
					+ "¡Escribe un correo electrónico a notorein@gmail.com \n" + "para obtener una versión actualizada!"
					+ "Tu versión actual es 2.1.0 \n" + "Los últimos cambios en el programa ocurrieron en 2020/11/21",
			"ACTUALIZAR", "OKAY", "Comuníquese con notorein@gmail.com para asegurarse \n"
					+ "que la siguiente dirección esté actualizada antes de donar!",
			"Resultados combinados"

	};

	static String[] fr = { "Créer un utilisateur", "Sélectionner un utilisateur", "Commence l'entraînement",
			"Montrer les résultats", "Sauvegardez les résultats", "Utilisateur: ", "Utilisateur",
			"L'utilisateur existe déjà! Écraser? Toutes les chaînes de langue seront perdues!", "Niveau: ",
			"Durée en minutes:", "Essais: ", "Visuel", "Auditiv", "Formation longue durée",
			"Changer d'utilisateur sans stocker les données?", "Oui", "Non", "Écraser les données?", "Sauver",
			"Annuler", "SORTIE", // 20
			"EN PAUSE", "APPUYEZ SUR ESC POUR CONTINUER", "Votre taux de bonnes réponses dans la tâche visuelle est",
			"Votre taux de bonnes réponses dans la tâche auditive est", " - retour", "Créer", "Tester le volume", "FR_",
			"RETOUR", // 29
			"ARRÊTEZ", "Lire le manuel", "Malheureusement, cette traduction n'a pas encore été vérifiée", "",
			// "Malheureusement, seul l'audio en anglais est actuellement
			// disponible pour cette langue",
			"Performance moyenne par jour", "Performance moyenne par session", "Afficher tous les runs",
			"APPUYEZ SUR LE BOUTON ESPACE POUR COMMENCER", // 37
			"Version N-BACK", "Le plus haut niveau atteint un jour", "Le plus haut niveau a atteint une session",
			"Survolez un point pour afficher la date de la session",
			"Voulez-vous quitter le programme sans enregistrer les données?",
			"© 2020 Notorein. Tous droits réservés! \n" // 43
					+ "La distribution commerciale de ce programme est strictement interdite! \n" // 43
					+ "La distribution gratuite de ce programme est autorisée! \n" // 43
					+ "Vous utilisez ce programme à vos risques et périls!", // 43
			"J'ai lu et compris les conditions!", // 44
			"Accepter les conditions!", // 45
			"Refuser les conditions!", "Son et position", "Son et couleur",
			"APPUYEZ SUR LE BOUTON ESPACE POUR TERMINER", // 49
			"Voulez-vous supprimer cet utilisateur? Toutes les données seront perdues!",
			"Quitter sans enregistrer? Les succès récents seront perdus!", "Veuillez choisir un utilisateur!",
			"Appuyez sur   " + visualMatch + "   pour confirmer les correspondances visuelles \n" + "Appuyez sur   "
					+ audioMatch + "   pour confirmer les correspondances audio",
			"Veuillez enregistrer les résultats avant de créer un nouvel utilisateur!", "Faire un don",
			"L'adresse Bitcoin est:", "Retour", "Copier L'adresse",
			"L'adresse a été copiée! \n" + "Vous pouvez la coller maintenant!",
			"Le domaine de mise à jour est en cours de maintenance. \n"
					+ "Veuillez écrire un e-mail à notorein@gmail.com \n" + "pour obtenir une version mise à jour! \n"
					+ "Votre version actuelle est 2.1.0 \n" + "Les dernières modifications apportées \n"
					+ "au programme ont eu lieu en 2020/11/21",
			"MISE À JOUR", "D'ACCORD", "Veuillez contacter notorein@gmail.com pour vous assurer \n"
					+ "que l'adresse suivante est à jour avant de faire un don!",
			"Résultats combinés"

	};

	static String[] ge = { "Benutzer Erstellen", // 0
			"Benutzer Auswählen", "Training Starten", "Ergebnisse", "Ergebnisse Speichern", "Benutzer: ", // 5
			"Benutzer", "Benutzer existiert bereits! Überschreiben? Alle Daten gehen verloren!", "Level: ",
			"Dauer in Minuten: ", "Runden: ", // 10
			"Visuell", "Auditiv", "Training der Daueraufmerksamkeit", "Den Benutzer wechseln ohne vorher zu speichern?",
			"Ja", // 15
			"Nein", "Daten Überschreiben?", "Speichern", "Abbrechen", "VERLASSEN", // 20
			"PAUSE", "ZUM FORTSETZEN ESC DRÜCKEN", "Ihre Erfolgsrate Teil Farbe beträgt",
			"Ihre Erfolgsrate im auditiven Teil beträgt", " - back", // 25
			"Erstellen", "Lautstärke Testen", "GE_", "ZURÜCK", "STOP", // 30
			"Handbuch", " ", " ",
			// "Leider wurde diese Übersetzung noch nicht überprüft",
			// "Leider ist derzeit nur die englische Audioausgabe für diese
			// Sprache verfügbar",
			"Durchschnittliche Tagesleistung", "Durchschnittliche Leistung pro Sitzung", "Zeige alle Durchgänge",
			"DRÜCKEN SIE DIE LEERTASTE UM ZU BEGINNEN", // 37
			"    N-BACK Version  ", "Höchste erreichte Stufe Tag", "Höchste erreichte Stufe Sitzung", // 40
			"Fahren Sie mit der Maus über die einzelnen Punkte, um das Datum anzuzeigen",
			"Wollen Sie das Programm beenden, ohne die Daten zu speichern?",
			"©2020 Notorein \n" // 43
					+ "Die kommerzielle Verbreitung dieses Programmes ist strengstens verboten! \n" // 43
					+ "Die freie Nutzung und Verteilung des Programmes ist gestattet! \n"
					+ "Die Nutzung dieses Programmes erfolgt auf eigene Verantwortung!", // 43
			"Ich habe die Bedingungen gelesen und verstanden!", // 44
			"Bedingungen akzeptieren!", // 45
			"Bedingungen ablehnen!", // 46
			"Sound und Position", "Sound und Farbe", "DRÜCKEN SIE DIE LEERTASTE",
			"Benutzer löschen? Alle Daten gehen verloren!",
			"Beenden ohne zu speichern? Aktuelle Erfolge gehen verloren!", "Wählen Sie einen Benutzer",
			"Drücken Sie   " + visualMatch + "   für visuelle Übereinstimmungen \n" + "Drücken Sie   " + audioMatch
					+ "   für auditive Übereinstimmungen",
			"Bitte speicher Sie die vorher die Ergebnisse!", "Spenden", "Die Bitcoin-Adresse lautet:", "Zurück",
			"Adresse kopieren", "Die Adresse wurde kopiert! \n" + "Sie können sie jetzt einfügen!",
			"Die Update-Domain wird gerade gewartet. \n" + "Bitte schreiben Sie eine E-Mail an notorein@gmail.com, \n"
					+ " um eine aktualisierte Version zu erhalten! \n" + "Ihre aktuelle Version ist 2.1.0 \n"
					+ "Letztes Programmänderungen im Jahr 2020/11/21",
			"AKTUALISIEREN", "OK", "Bitte kontaktieren Sie notorein@gmail.com\n"
					+ "um sicherzustellen, dass die folgende Addresse\n" + "noch gültig ist!",
			"Kombinierte Ergebnisse"

	};

	static String[] jp = { "ユーザーを作成", "ユーザーを選択し", "トレーニングを開始し", "結果を示す", "結果を保存する", "ユーザー： ", "ユーザー",
			"このユーザーは既に存在します！ \n上書きしますか？すべてのデータが失われます", "レベル： ", "分単位の期間： ", "試験： ", "ビジュアル", "聴覚の", "長時間トレーニング",
			"データを保存せずにユーザーを変更しますか？", "はい", "番号", "データを上書きしますか", "セーブ", "取り消す", "出口", "一 時停止", "ESC キーを押して続行します",
			"視覚的課題におけるあなたの正解率は", "オーディオタスクでの正解率は", " - うしろ え ", "つくる", "試験量", "JP_", "戻る", "停止", // 30
			"マニュアルを読む", "残念ながらこの翻訳はまだチェックされていません", "",
			// "残念ながら,現在この言語で利用できるのは英語の音声のみです",
			"1日あたりの平均パフォーマンス。", "セッションあたりの平均パフォーマンス", "すべての実行を表示", "スペースボタンを押して開始", // 37
			"歩-うしろ え  バージョン", "最高レベルは一日に達しました", "最高レベルのセッションに到達しました", "ポイントにカーソルを合わせると,セッションの日付が表示されます",
			"データを保存せずにプログラムを終了しますか？",
			"©2020 Notorein。All rights reserved！ \n" // 43
					+ "このプログラムの商用配布は固く禁じられています！ \n" // 43
					+ "このプログラムの無料配布は許可されています！ \n"// 43
					+ "このプログラムは自己責任で使用してください！", // 43
			"私は条件を読んで理解しました！", // 44
			"条件を受け入れます！", // 45
			"条件を拒否してください！", "音と位置", "音と色", "スペースボタンを押して終了します", // 49
			"そのユーザーを削除しますか？すべてのデータは失われます!", "保存せずに終了しますか？最近の実績は失われます！", "ユーザーを選択してください",
			visualMatch + "  ボタンを押して,視覚的な一致を確認します。 \n" + audioMatch + "  ボタンを押して,音声の一致を確認します。",
			"新しいユーザーを作成する前に,結果を保存してください！", "寄付", "ビットコインのアドレスは：", "バック", "アドレスをコピー",
			"アドレスがコピーされました！ \n" + "今すぐ貼り付けることができます！",
			"更新ドメインはメンテナンス中です。 \n" + "更新バージョンを入手するにはnotorein@gmail.comに電子メールを書いてください！ \n" + "現在のバージョンは2.1.0です \n"
					+ "プログラムの最後の変更は2020/11/21に発生しました",
			"更新", "OK", "確認するには、notorein @ gmail.comにお問い合わせください\n" + "寄付する前に次のアドレスが最新であること！", "結合された結果" };

	static String[] kr = { "사용자 만들기", "사용자를 선택하고", "훈련 시작", "결과 보여줘", "결과 저장", "사용자: ", "사용자",
			"사용자가 이미 존재합니다 덮어 쓰시겠습니까 \n모든 데이터가 손실됩니다", "레벨 : ", "시간 (분): ", "시험판 : ", // 10
			"시각", "청각", "장시간 훈련", "데이터를 저장하지 않고 사용자를 변경 하시겠습니까", "예", // 15
			"아니", "데이터 덮어 쓰기", "저장", "취소", "출구", // 20
			"일시 중지", "계속하려면 ESC를 누르십시오", "시각적 과제에서 정답 비율은 ", "오디오 작업에서 정답 비율은 ", " - 뒤로", // 25
			"창조하다", "시험량", "KR_", "돌아 가기", "중지", // 30
			"설명서 읽기", "불행히도이 번역은 아직 확인되지 않았습니다.", "",
			// "불행히도 현재이 언어에는 영어 오디오 만 사용할 수 있습니다",
			"일일 평균 성능", "세션 당 평균 성능", "모든 런 표시", "공간 버튼을 눌러 시작", // 37
			"N-BACK 버전", "최고 수준에 도달했습니다", "최상위 수준의 세션에 도달했습니다", "세션 날짜를 표시하려면 포인트 위로 마우스를 가져갑니다",
			"데이터를 저장하지 않고 프로그램을 종료 하시겠습니까?",
			"© 2020 Notorein. 판권 소유! \n"// 43
					+ "이 프로그램의 상업적 배포는 엄격히 금지됩니다! \n"// 43
					+ "이 프로그램의 무료 배포가 허용됩니다! \n"// 43
					+ "이 프로그램은 자신의 책임하에 사용합니다!", // 43
			"나는 조건을 읽고 이해했습니다!", // 44
			"조건 수락!", // 45
			"거부 조건!", // 46
			"소리와 위치", "소리와 색상", "완료하려면 스페이스 버튼을 누르세요", // 49
			"해당 사용자를 삭제 하시겠습니까? 모든 데이터는 손실됩니다!", "저장하지 않고 종료 하시겠습니까? 최근의 실적은 손실됩니다!", "사용자를 선택하십시오",
			visualMatch + "  버튼을 눌러 시각적 일치를 확인합니다 \n" + audioMatch + "  버튼을 눌러 오디오 일치를 확인합니다",
			"새 사용자를 만들기 전에 결과를 저장하십시오!", "기부", "비트 코인 주소 :", "뒤", "주소 복사", "주소가 복사되었습니다! \n" + "이제 붙여 넣을 수 있습니다!",
			"업데이트 도메인이 유지 관리 중입니다. \n" + "업데이트 된 버전을 받으려면 notorein@gmail.com으로 이메일을 보내주세요! \n" + "현재 버전은 2.1.0입니다. \n"
					+ "프로그램의 마지막 변경 사항은 2020/11/21에 발생했습니다",
			"최신 정보", "확인", "확인하려면 notorein@gmail.com에 문의하십시오 \n" + "기부하기 전에 다음 주소가 최신 정보입니다!", "결합 된 결과" };

	static String[] it = { "Creare un utente", "Seleziona utente", "Inizia l'allenamento", "Mostra i risultati",
			"Memorizza risultati", "Utente: ", "Utente",
			"L'utente esiste già! Sovrascrivi? Tutte le lingueString andranno perse!", "Livello:", "Durata: ", "Prove:",
			"Visivo", "Uditiva", "Formazione a lungo termine", "Cambia utente senza salvare?", "Sì", "No",
			"Sovrascrivi dati?", "Salva", "Annulla", "USCITA", // 20
			"IN PAUSA", "PREMERE ESC PER CONTINUARE", "La tua percentuale di risposte giuste nell'attività visiva è",
			"La tua percentuale di risposte corrette nell'attività uditiva è", " - indietro", "Creare",
			"Volume di prova", "IT_", "INDIETRO", "FERMARE", "Leggi il manuale",
			"Purtroppo questa traduzione non è stata ancora verificata",
			"Sfortunatamente, al momento è disponibile solo l'audio inglese per questa lingua",
			"Produzione media giornaliera", "Rendimento medio per sessione", "Mostra tutte le piste",
			"PREMERE IL PULSANTE SPAZIO PER INIZIARE", // 37
			"Versione N-BACK", "Il livello più alto ha raggiunto un giorno",
			"Il livello più alto ha raggiunto una sessione", "Passa il mouse sopra ogni punto per vedere la data" };

	static String[] ak = { "Skep gebruiker", "Kies gebruiker", "Begin opleiding", "Wys resultate", "Winkeluitslae",
			"Gebruiker:", "Gebruiker", "Gebruiker bestaan reeds! Oorskryf? Alle taalstringe sal verlore gaan!", "Vlak:",
			"Duur:", "Proewe:", "Visual", "Ouditiewe", "Langdurige opleiding", "Verander gebruiker sonder om te stoor?",
			"Ja", // 15
			"Geen", "Herskryf data?", "Stoor data", "Kanseleer", "UITGANG", // 20
			"LAAT WAG", "PERS ESC OM TE VERVOLG", "U hoeveelheid regte antwoorde in die visuele taak is",
			"U persentasie regte antwoorde in die gehoortaak is", " - terug", // 25
			"Skep", "Toetsvolume", "AK_", "TERUG", "STOP", "Læs manualen",
			"Desværre er denne oversættelse endnu ikke blevet kontrolleret",
			"Desværre er der kun engelsk tilgængelig i øjeblikket til dette sprog", "Gennemsnitlig daglig produktion",
			"Gennemsnitlig ydelse pr. Session", "Vis alle kørsler", "TRYK RUDKNAPPET FOR AT BEGYNDE", // 37
			"N-BACK version", "Højeste niveau nået en dag", "Højeste niveau nåede en session",
			"Hold markøren over hvert punkt for at se datoen" };
	static String[] cz = { "Vytvořit uživatele", "Vyberte uživatele", "Zahájit trénink", "Ukázat výsledky",
			"Výsledky obchodu", "Uživatel:", "Uživatel",
			"Uživatel již existuje! Přepsat? Všechny jazykové řetězce budou ztraceny!", "Úroveň: ", "Doba trvání: ",
			"Zkoušky:", "Vizuální", "Sluchový", "Dlouhodobý výcvik", "Změnit uživatele bez uložení?", "Ano", "Ne",
			"Přepsat data?", "Uložit", "Zrušení", "VÝSTUP", "PAUZOVANÝ", "STISKNĚTE ESC K POKRAČOVÁNÍ",
			"Vaše míra správných odpovědí ve vizuální úloze je", "Vaše míra správných odpovědí v posluchárně je",
			" - zpět", "Vytvořit", "Zkušební objem", "CZ_", "ZADNÍ", "STOP", "Přečtěte si příručku",
			"Bohužel tento překlad ještě nebyl zkontrolován",
			"Bohužel je pro tento jazyk v současné době k dispozici pouze anglický zvuk", "Průměrný denní výkon",
			"Průměrný výkon za relaci", "Zobrazit všechny běhy", "STISKNĚTE TLAČÍTKO MEZERNÍKU", // 37
			"N-BACK verze", "Nejvyšší úroveň za den", "Nejvyšší úroveň dosáhla relace",
			"Umístěním kurzoru na každý bod zobrazíte datum"

	};

	static String[] dn = { "Opret bruger", "Vælg bruger", "Start træning", "Vis resultater", "Store resultater",
			"Bruger:", "Bruger", "Bruger findes allerede! Overskrivning? Alle sprogstreng vil gå tabt!", "Niveau:",
			"Varighed:", "Forsøg:", "Visuel", "Auditiv", "Langtidstræning", "Skift bruger uden at gemme?", "Ja",
			"Ingen", "Overskriv data?", "Gemme", "Afbestille", "AFSLUT", // 20
			"PAUSE", "TRYK PÅ ESC FOR AT FORSÆTTE", "Din rate af rigtige svar i den visuelle opgave er",
			"Din rate af rigtige svar i den auditive opgave er", " - tilbage", "Skab", "Testvolumen", "DN_", "TILBAGE",
			"HOLD OP", "Brugervejledning", "Desværre er denne oversættelse endnu ikke blevet kontrolleret",
			"Desværre er der kun engelsk tilgængelig i øjeblikket til dette sprog", "Gennemsnitlig daglig produktion",
			"Gennemsnitlig ydelse pr. Session", "Vis alle kørsler", "TRYK RUDKNAPPET FOR AT BEGYNDE", // 37
			"N-BACK version", "Højeste niveau nået en dag", "Højeste niveau nåede en session",
			"Hold markøren over hvert punkt for at se datoen" };

	static String[] est = { "Loo kasutaja", "Valige kasutaja", "Alusta treenimist", "Näita tulemusi", "Store Results",
			"Kasutaja:", "Kasutaja", "Kasutaja on juba olemas! Kirjutage üle. Kõik keelerihmad kaovad!", "Tase:",
			"Kestus:", "Prooviversioonid", "Visuaalne", "Auditoorne", "Pikaajaline koolitus",
			"Kas muuta kasutajat salvestamata?", "Jah", "Ei", "Kas andmed üle kirjutada?", "Salvesta", "Tühista",
			"VÄLJUMINE", // 20
			"PEATATUD", "JÄTKAKE ESC", "Teie visuaalse ülesande õigete vastuste määr on",
			"Teie õigete vastuste määr kuulmisülesandes on", " - tagasi", "Loo", "Testimaht", "EST_", "TAGASI",
			"PEATUS", "Loe juhendit", "Kahjuks pole seda tõlget veel kontrollitud",
			"Kahjuks on selle keele jaoks praegu saadaval ainult ingliskeelne heli", "Keskmine päevane toodang",
			"Keskmine toimivus seansi kohta", "Kuva kõik jooksud", "VAJUTA RUUMINUPU ALGATAMISEKS", // 37
			"N-BACK versioon", "Päeva kõrgeim tase saavutatud", "Kõige kõrgem tase saavutas istungi",
			"Kujutage kursorit kuupäeva nägemiseks iga punkti kohal" };

	static String[] firstStart = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
			"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
			"", "",

	};

	static String[] fl = { "Lumikha ng Gumagamit", "Piliin ang Gumagamit", "Simulan ang Pagsasanay",
			"Ipakita ang Mga Resulta", "Mga Resulta ng Store", "Gumagamit:", "Gumagamit",
			"Nagagamit na ang gumagamit! Overwrite? Lahat ng wikaStrings ay mawawala!", "Antas:", "Tagal: ",
			"Mga Pagsubok:", "Visual", "Auditoryo", "Long Time Training",
			"Baguhin ang gumagamit nang hindi nakakatipid?", "Oo", "Hindi", "Overwrite Data?", "I-save", "Pagkansela",
			"LUMABAS", // 20
			"PAUSE", "PINDUTIN ANG ESC UPANG MAGPATULOY", "Ang iyong rate ng mga tamang sagot sa visual na gawain ay",
			"Ang iyong rate ng mga tamang sagot sa gawain ng pandinig ay", " - pabalik", "Lumikha", "Dami ng Pagsubok",
			"FL_", "BALIK", "TUMIGIL", "Basahin ang Manwal",
			"Sa kasamaang palad, ang pagsasaling ito ay hindi pa nasuri",
			"Sa kasamaang palad, tanging audio ng Ingles ang magagamit para sa wikang ito",
			"Average araw-araw na output", "Average na pagganap sa bawat session", "Ipakita ang lahat ng tumatakbo",
			"PRESS ANG SPACE BUTTON NA MAGING MABUTI", // 37
			"Bersyon ng N-BACK", "Umabot ang pinakamataas na antas sa isang araw",
			"Naabot ng kataas na antas ang isang session", "Mag-hover sa bawat punto upang makita ang petsa" };

	static String[] fsk = { "Luo käyttäjä", "Valitse käyttäjä", "Aloita harjoitus", "Näytä tulokset",
			"Tallenna tulokset", "Käyttäjä:", "Käyttäjä",
			"Käyttäjä on jo olemassa! Korvaa yli? Kaikki languageStrings menetetään!", "Taso: ", "Kesto:", "Kokeilut:",
			"Visuaalinen", "Kuulo", "Pitkäaikainen koulutus", "Vaihdako käyttäjää tallentamatta?", "Joo", "Ei",
			"Korvaako data?", "Tallentaa", "Peruuttaa", "POISTUA", // 21
			"KESKEYTETYN", "PAINA ESC JATKAAKSESI", "Sinun oikeiden vastausten määrä visuaalisessa tehtävässä on",
			"Oikeiden vastausten määrä kuulokysymyksessä on", " - takaisin", "Luoda", "Testimäärä", "FSK_", "TAKAISIN",
			"LOPETTAA", "Lue käyttöohje", "Valitettavasti tätä käännöstä ei ole vielä tarkistettu",
			"Valitettavasti tällä kielellä on tällä hetkellä saatavana vain englanninkielinen ääni",
			"Keskimääräinen päivätuotos", "Keskimääräinen teho istuntoa kohti", "Näytä kaikki juoksut",
			"PAINA TILPAINIKKE ALOITTAMISEKSI", // 37
			"N-BACK-versio", "Korkeimman tason saavutettu päivä", "Korkein taso saavutti istunnon",
			"Vie hiiri kunkin pisteen päälle nähdäksesi päivämäärän" };

	static String[] geo = {

			"შექმენით მომხმარებელი", "აირჩიეთ მომხმარებელი", "დაიწყე ტრენინგი", "Შედეგების ჩვენება",
			"მაღაზიის შედეგები", "მომხმარებელი:", "მომხმარებელი",
			"მომხმარებელი უკვე არსებობს! გადაწერა? ყველა ენა დაკარგულია!", "დონე:", "ხანგრძლივობა:", "ცდები:",
			"ვიზუალური", "აუდიტორია", "გრძელი ტრენინგი", "შეცვალეთ მომხმარებელი დაზოგვის გარეშე?", "დიახ", "არა",
			"გადატვირთვის მონაცემები?", "Გადარჩენა", "გაუქმება", "გასასვლელი", "პაუზა", "პრეს ESC გაგრძელდეს",
			"ვიზუალური ამოცანაში სწორი პასუხების ტოლია", "თქვენი პასუხების სწორი პასუხი აუდიტორულ დავალებაშია",
			" - უკან", "Შექმნა", "ტესტის მოცულობა", "GEO_", "უკან", "გაჩერება", // 30
			"წაიკითხეთ სახელმძღვანელო", "სამწუხაროდ, ეს თარგმანი ჯერ არ არის შემოწმებული",
			"სამწუხაროდ, ამჟამად მხოლოდ ინგლისური აუდიოა ხელმისაწვდომი ამ ენაზე", "საშუალო დღიური გამომავალი",
			"საშუალო შესრულება თითო სესიაზე", "აჩვენეთ ყველა სირბილი", "დააჭირეთ სივრცის გახსნას", // 37
			"N-BACK ვერსია", "უმაღლესი დონის დღეში მიაღწია", "უმაღლესი დონის მიღწევა სხდომაზე",
			"გადაიტანეთ თითოეულ წერტილზე, რომ ნახოთ თარიღი" };

	static String[] gr = { "Δημιουργία χρήστη", "Επιλογή χρήστη", "Έναρξη εκπαίδευσης", "Εμφάνιση αποτελεσμάτων",
			"Αποτελέσματα καταστήματος", "Χρήστης: ", "Χρήστης",
			"Ο χρήστης υπάρχει ήδη! Αντικατάσταση; Όλες οι γλωσσικές συμβολοσειρές θα χαθούν!", "Επίπεδο:",
			"Διάρκεια: ", "Δοκιμές:", "Οπτικός", "Ακουστικός", "Μακροχρόνια προπόνηση",
			"Αλλαγή χρήστη χωρίς αποθήκευση;", "Ναί", "Οχι", "Αντικατάσταση δεδομένων;", "Σώσει", "Ματαίωση", "ΕΞΟΔΟΣ",
			"ΠΑΖΕ", // 21
			"ΠΑΤΉΣΤΕ ESC ΓΙΑ ΝΑ ΣΥΝΕΧΊΣΕΤΕ", "Το ποσοστό των σωστών απαντήσεων στην οπτική εργασία είναι",
			"Το ποσοστό των σωστών απαντήσεων στην ακουστική εργασία είναι", " - πίσω", "Δημιουργώ", "Όγκος δοκιμής",
			"GR_", "ΠΙΣΩ", "ΝΑ ΣΤΑΜΑΤΗΣΕΙ", "Διαβάστε το εγχειρίδιο",
			"Δυστυχώς, αυτή η μετάφραση δεν έχει ελεγχθεί ακόμη",
			"Δυστυχώς, μόνο αγγλικός ήχος είναι προς το παρόν διαθέσιμος για αυτήν τη γλώσσα", "Μέση ημερήσια παραγωγή",
			"Μέση απόδοση ανά συνεδρία", "Εμφάνιση όλων των τρεξίματος", "ΠΙΕΣΤΕ ΤΟ ΚΟΥΜΠΙ ΧΩΡΟΥ ΓΙΑ ΝΑ ΞΕΚΙΝΗΣΕΤΕ", // 37
			"Έκδοση N-BACK", "Το υψηλότερο επίπεδο έφτασε μια μέρα", "Το υψηλότερο επίπεδο έφτασε σε μια συνεδρία",
			"Τοποθετήστε τον δείκτη του ποντικιού πάνω από κάθε σημείο για να δείτε την ημερομηνία" };

	static String[] hd = { "उपयोगकर्ता बनाइये", "उपयोगकर्ता चुनें", "प्रशिक्षण शुरू करो", "परिणाम दिखाओ",
			"स्टोर परिणाम", "उपयोगकर्ता", "उपयोगकर्ता",
			"उपयोगकर्ता पहले से मौजूद है! ओवरराइट करें! सभी भाषाएं खो जाएँगी!", "स्तर:", "अवधि: ", "परीक्षण:", "दृश्य",
			"श्रवण", "लॉन्ग टाइम ट्रेनिंग", "उपयोगकर्ता को बचाने के बिना बदलें;", "हाँ", "नहीं",
			"डेटा को अधिलेखित करें?", "सहेजें", "रद्द करना", "बाहर जाएं", "रोके गए", "संपर्क करने के लिए ESC को दबाएं",
			"दृश्य कार्य में सही उत्तरों की आपकी दर है", "श्रवण कार्य में सही उत्तरों की आपकी दर है", " - वापस",
			"सृजन करना", "टेस्ट वॉल्यूम", "HD_", "वापस", "रुकें", "मैनुअल पढ़ें",
			"दुर्भाग्य से इस अनुवाद की अभी तक जाँच नहीं हुई है।",
			"दुर्भाग्य से, वर्तमान में केवल अंग्रेजी ऑडियो इस भाषा के लिए उपलब्ध है।", "औसत दैनिक उत्पादन",
			"औसत प्रदर्शन प्रति सत्र", "सभी रन दिखाएं", "बेसिन के लिए अंतरिक्ष बटन दबाएं", // 37
			"एन-बैक संस्करण", "एक दिन में उच्चतम स्तर तक पहुँच गया", "उच्चतम-स्तर एक सत्र में पहुंचा",
			"तिथि देखने के लिए प्रत्येक बिंदु पर होवर करें" };

	static String[] ir = { "Cruthaigh Úsáideoir", "Roghnaigh Úsáideoir", "Tosaigh Oiliúint", "Taispeáin Torthaí",
			"Torthaí Stórála", "Úsáideoir:", "Úsáideoir",
			"Tá an t-úsáideoir ann cheana féin! Forscríobh? Caillfear gach teangaStrings!", "Leibhéal:", "Fad:",
			"Trialacha:", "Amharc", "Iniúchóireacht", "Oiliúint Fadó", "Athraigh úsáideoir gan sábháil?", "Tá", "Níl",
			"Sonraí a fhorscríobh?", "Sábháil", "Cealaigh", "AN SLÍ AMACH", "AR SOS",
			"BRÚIGH ESC CHUN LEANÚINT AR AGHAIDH", "Is é do ráta freagraí cearta sa tasc amhairc",
			"Is é do ráta freagraí cearta sa tasc iniúchta", " - ar ais", "Cruthaigh", "Toirt Tástála", "IR_", "Ar ais",
			"stad", // 30
			"Léigh Lámhleabhar", "Ar an drochuair, níor seiceáladh an t-aistriúchán seo go fóill",
			"Ar an drochuair, níl ach fuaim Bhéarla ar fáil don teanga seo faoi láthair", "Meán-aschur laethúil",
			"Meánfheidhmíocht in aghaidh an tseisiúin", "Taispeáin gach rith", "PRESS THE SPACE BUTTON TO BEGIN", // 37
			"Leagan N-BACK", "Shroich an leibhéal is airde lá", "Shroich an leibhéal is airde seisiún",
			"Téigh os cionn gach pointe chun an dáta a fheiceáil" };

	static String[] is = { "Búa til notanda", "Veldu notanda", "Byrja þjálfun", "Sýna árangur", "Store niðurstöður",
			"Notandi:", "Notandi", "Notandi er þegar til! Yfirskrifa? Allir tungumálastrengir munu glatast!", "Stig:",
			"Lengd: ", "Rannsóknir:", "Sjónræn", "Hljóðrænt", "Langþjálfun", "Breyta notanda án þess að vista?", "Já",
			"Nei", "Skrifa yfir gögn?", "Vista", "Hætta við", "ÚTGANGUR", "GERA HLÉ", "ÝTTU Á ESC TIL AÐ HALDA ÁFRAM",
			"Hlutfall réttra svara í sjónrænu verkefni er", "Hlutfall réttra svara í heyrnarverkefninu er",
			" - til baka", "Búa til", "Prófmagn", "IS_", "TIL BAKA", "stopp", // 30
			"Lestu handbók", "Því miður hefur þessi þýðing ekki enn verið athuguð",
			"Því miður er aðeins enskt hljóð tiltækt sem stendur fyrir þetta tungumál", "Meðal dagleg framleiðsla",
			"Meðalárangur á lotu", "Sýna allar keyrslur", "PRENTAÐU RÚMLEGA KNAPPINU TIL BEGIN", // 37
			"N-BACK útgáfa", "Hæsta stigi náð á dag", "Hæsta stigi náði þingi",
			"Sveimdu yfir hvert stig til að sjá dagsetninguna" };

	public static ArrayList<String[]> lngLst = new ArrayList<String[]>();

	static String[] nl = { "Maak gebruiker aan", // 0
			"Selecteer gebruiker", "Begin met trainen", "Resultaten", "Resultaten opslaan", "Gebruiker:", // 5
			"Gebruiker", "Gebruiker bestaat al! Overschrijven? Alle gegevens gaan verloren!", "Niveau:", "Looptijd:",
			"Rond:", // 10
			"Visueel", "Auditief", "Continue aandachtstraining", "Gebruiker wijzigen zonder op te slaan?", "Ja", // 15
			"Nee", "Gegevens overschrijven?", "Opslaan", "Afbreken", "VERLATEN", // 20
			"BREKEN", "DRUK OP ESC OM DOOR TE GAAN", "Uw slagingspercentage in het visuele deel is",
			"Uw slagingspercentage in het auditieve deel is", " - terug", // 25
			"Maken", "Testvolume", "NL_", "TERUG", "STOP", // 30
			"Lees handleiding", "Helaas is deze vertaling nog niet gecontroleerd",
			"Helaas is er momenteel alleen Engelse audio beschikbaar voor deze taal", "Gemiddelde dagelijkse output",
			"Gemiddelde prestatie per sessie", "Toon alle runs", "DRUK OP DE RUIMTEKNOP OM TE BEGINNEN", // 37
			"N-BACK-versie", "Hoogste niveau bereikt dag", "Het hoogste niveau heeft een sessie bereikt",
			"Plaats de muisaanwijzer op elk punt om de datum te zien" };

	static String[] nw = { "Opprett bruker", "Velg bruker", "Start trening", "Vis resultater", "Butikkresultater",
			"Bruker:", "Bruker", "Bruker eksisterer allerede! Overskriv? Alle språkspråk vil gå tapt!", "Nivå:",
			"Varighet:", "Forsøk:", "Visuell", "Auditory ", "Langtidstrening", "Endre bruker uten å lagre?", "Ja",
			"Nei", "Overskrive data?", "Lagre", "Avbryt", "EXIT", "PAUSET", "TRYKK ESC FOR Å FORTSETTE",
			"Din rate av riktige svar i den visuelle oppgaven er",
			"Din rate av riktige svar i den auditive oppgaven er", " - tilbake", "Skape", "Testvolum", "NW_", "TILBAKE",
			"STOPP", // 30
			"Les manual", "Dessverre har denne oversettelsen ennå ikke blitt sjekket",
			"Dessverre er det bare engelsk lyd som er tilgjengelig for dette språket for øyeblikket",
			"Gjennomsnittlig daglig produksjon", "Gjennomsnittlig ytelse per økt", "Vis alle løpeturer",
			"TRYKK RUDDKNAPPEN FOR Å BLI", // 37
			"N-BACK versjon", "Høyeste nivå nådd en dag", "Høyeste nivå nådde en økt",
			"Hold musepekeren over hvert punkt for å se datoen" };

	static String[] pj = { "ਉਪਭੋਗਤਾ ਬਣਾਓ", "ਉਪਭੋਗਤਾ ਚੁਣੋ", "ਸਿਖਲਾਈ ਅਰੰਭ ਕਰੋ", "ਨਤੀਜੇ ਦਿਖਾਓ", "ਸਟੋਰ ਨਤੀਜੇ", "ਉਪਭੋਗਤਾ:",
			"ਉਪਭੋਗਤਾ", "ਯੂਜ਼ਰ ਪਹਿਲਾਂ ਹੀ ਮੌਜੂਦ ਹੈ! ਉੱਤੇ ਲਿਖੋ? ਭਾਸ਼ਾਵਾਂ ਦੀਆਂ ਸਾਰੀਆਂ ਭਾਸ਼ਾਵਾਂ ਖਤਮ ਹੋ ਜਾਣਗੀਆਂ!", "ਪੱਧਰ:",
			"ਅਵਧੀ:", "ਟਰਾਇਲ:", "ਵਿਜ਼ੂਅਲ", "ਆਡਿਟਰੀ", "ਲੰਮੇ ਸਮੇਂ ਦੀ ਸਿਖਲਾਈ", "ਉਪਭੋਗਤਾ ਨੂੰ ਬਚਾਏ ਬਿਨਾਂ ਬਦਲੋ?", "ਹਾਂ",
			"ਨਹੀਂ", "ਡਾਟਾ ਉੱਤੇ ਲਿਖੋ?", "ਸੇਵ", "ਰੱਦ ਕਰੋ", "ਨਿਕਾਸ", "ਰੁਕਿਆ", "ਜਾਰੀ ਰੱਖਣ ਲਈ ਦਬਾਓ ESC",
			"ਵਿਜ਼ੂਅਲ ਟਾਸਕ ਵਿੱਚ ਤੁਹਾਡੇ ਸਹੀ ਜਵਾਬਾਂ ਦੀ ਦਰ ਹੈ", "ਆਡੀਟਰੀ ਟਾਸਕ ਵਿੱਚ ਤੁਹਾਡੇ ਸਹੀ ਜਵਾਬਾਂ ਦੀ ਦਰ ਹੈ", " - ਵਾਪਸ",
			"ਬਣਾਓ", "ਟੈਸਟ ਵਾਲੀਅਮ", "PJ_", "ਵਾਪਸ", "ਰੋਕੋ", // 30
			"ਮੈਨੂਅਲ ਪੜ੍ਹੋ", "ਬਦਕਿਸਮਤੀ ਨਾਲ ਇਸ ਅਨੁਵਾਦ ਦੀ ਅਜੇ ਜਾਂਚ ਨਹੀਂ ਕੀਤੀ ਗਈ",
			"ਬਦਕਿਸਮਤੀ ਨਾਲ, ਇਸ ਭਾਸ਼ਾ ਲਈ ਇਸ ਸਮੇਂ ਸਿਰਫ ਅੰਗਰੇਜ਼ੀ ਆਡੀਓ ਉਪਲਬਧ ਹੈ", "ਰੋਜ਼ਾਨਾ ਮੁੱਲ",
			"ਪ੍ਰਤੀ ਸੈਸ਼ਨ ਦੀ performanceਸਤਨ ਪ੍ਰਦਰਸ਼ਨ", "ਸਾਰੇ ਦੌੜਾਂ ਦਿਖਾਓ", "ਸ਼ੁਰੂ ਕਰਨ ਲਈ ਸਪੇਸਬਰ ਦਬਾਓ", "ਐਨ-ਬੈਕ ਵਰਜਨ",
			"ਇਕ ਦਿਨ ਦਾ ਉੱਚਤਮ-ਪੱਧਰ ਪਹੁੰਚਿਆ", "ਉੱਚ-ਪੱਧਰ ਦਾ ਇੱਕ ਸੈਸ਼ਨ ਪਹੁੰਚਿਆ", "ਮਿਤੀ ਵੇਖਣ ਲਈ ਹਰੇਕ ਬਿੰਦੂ ਤੇ ਹੋਵਰ ਕਰੋ" };

	static String[] pl = { "Stwórz użytkownika", "Wybierz użytkownika", "Zacznij trenować", "Pokaż wyniki",
			"Wyniki sklepu", "Użytkownik:", "Użytkownik",
			"Użytkownik już istnieje! Zastąpić? Wszystkie ciągi językowe zostaną utracone!", "Poziom:", "Trwanie: ",
			"Próby:", "Wizualny", "Słuchowy", "Long Time Training", "Zmienić użytkownika bez zapisywania?", "Tak",
			"Nie", "Zastąpić dane?", "Zapisać", "Anuluj", "WYJŚCIE", "WSTRZYMANY", "NACIŚNIJ ESC, ABY KONTYNUOWAĆ",
			"Twój wskaźnik prawidłowych odpowiedzi w zadaniu wizualnym wynosi",
			"Twój wskaźnik prawidłowych odpowiedzi w zadaniu słuchowym wynosi", " - plecy", "Stwórz",
			"Objętość testowa", "PL_", "PLECY", "STOP", // 30
			"Przeczytaj instrukcję", "Niestety to tłumaczenie nie zostało jeszcze sprawdzone",
			"Niestety, tylko angielski dźwięk jest obecnie dostępny dla tego języka", "Średnia dzienna wydajność",
			"Średnia wydajność na sesję", "Pokaż wszystkie przebiegi", "NACISNĄĆ PRZYCISK SPACJI, ABY ROZPOCZĄĆ", // 37
			"Wersja N-BACK", "Najwyższy poziom osiągnął dzień", "Najwyższy poziom osiągnął sesję",
			"Najedź kursorem na każdy punkt, aby zobaczyć datę" };

	static String[] pt = { "Criar usuário", "Selecionar usuário", "Comece a treinar", "Mostrar resultados",
			"Resultados da loja", "Do utilizador: ", "Do utilizador",
			"O usuário já existe! Substituir? Todos os languageStrings serão perdidos!", "Nível: ", "Duração: ",
			"Ensaios: ", "Visual", "Auditivo", "Treinamento de longa data", "Alterar usuário sem salvar?", "Sim", "Não",
			"Substituir dados?", "Salve ", "Cancelar", "SAÍDA", "PAUSADO", "PRESSIONE ESC PARA CONTINUAR",
			"Sua taxa de respostas corretas na tarefa visual é", "Sua taxa de respostas corretas na tarefa auditiva é",
			" - de volta", "Crio", "Volume de teste", "PT_", "DE VOLTA", "STOP", // 30
			"Leia o manual", "Infelizmente esta tradução ainda não foi verificada",
			"Infelizmente, atualmente apenas o áudio em inglês está disponível para este idioma",
			"Produção média diária", "Desempenho médio por sessão", "Mostrar todas as execuções",
			"PRESSIONE O BOTÃO ESPAÇO PARA COMEÇAR", // 37
			"Versão N-BACK", "O nível mais alto alcançou um dia", "O nível mais alto alcançou uma sessão",
			"Passe o mouse sobre cada ponto para ver a data" };

	static String[] ru = {

			"Создать пользователя", "Выбрать пользователя", "Начать обучение", "Показать результаты",
			"Результаты магазина", "Пользователь:", "Пользователь",
			"Пользователь уже существует! Перезаписать? Все языковые строки будут потеряны!", "Уровень:",
			"Продолжительность:", "Испытания", "Визуальный", "Auditiv", "Долгое время обучения",
			"Изменить пользователя без сохранения?", "Да", "Нет", "Перезаписать данные?", "Сохранить", "Отмена",
			"ВЫХОД", // 20
			"ПРИОСТАНОВЛЕНО", "НАЖМИТЕ ESC, ЧТОБЫ ПРОДОЛЖАТЬ",
			"Ваша скорость правильных ответов в визуальном задании равна",
			"Ваша оценка правильных ответов в слуховом задании равна", " - назад", "Создайте", "Тестовый объем", "RU_",
			"НАЗАД", "СТОП", // 30
			"Читать руководство", "К сожалению, этот перевод еще не проверен",
			"К сожалению, в настоящее время для этого языка доступно только английское аудио",
			"Среднесуточная выработка", "Средняя производительность за сеанс", "Показать все пробеги",
			"НАЖМИТЕ КОСМИЧЕСКУЮ КНОПКУ, ЧТОБЫ НАЧАТЬ", // 37
			"N-BACK версия", "Достигнут самый высокий уровень за день", "Высший уровень достиг сеанса",
			"Наведите курсор на каждую точку, чтобы увидеть дату" };

	static String[] sc = { "创建用户", "选择用户", "开始培训", "显示结果", "存储结果", "用户：", "用户", "用户已经存在！覆盖？所有语言字符串将丢失！", "级别：", "持续时间：",
			"试用：", "视觉", "听觉", "长时间培训", "更改用户而不保存？", "是", "没有", "覆盖数据？", "救", "取消", "出口", "已暂停", "按ESC键继续",
			"您在视觉任务中正确答案的比率为", "您在听觉任务中的正确答案率为", " - 背部", "创建", "测试量", "SC_", "背部", "停止", // 30
			"阅读手册", "很遗憾,此翻译尚未检查", "很遗憾,该语言目前仅提供英语音频", "平均每日产量", "每个会话的平均效果", "显示所有运行", "按空格键开始", // 37
			" N-BACK 版本", "最高级别达到了一天", "最高级别的会议", "将鼠标悬停在每个点上以查看日期" };

	static String[] sct = { "Cruthaich cleachdaiche", "Tagh cleachdaiche", "Tòisich air trèanadh", "Seall Toraidhean",
			"Toraidhean stòr", "Cleachdaiche:", "Cleachdaiche",
			"Tha an cleachdaiche ann mu thràth! Ath-sgrìobhadh? Thèid a h-uile cànanStrings a chall!", "Ìre:", "Faid:",
			"Deuchainnean:", "Lèirsinneach", "Sgrùdadh", "Trèanadh Ùine", "Atharraich an cleachdaiche gun sàbhaladh?",
			"Tha", "Chan eil", "Cuir thairis dàta?", "Sàbhail", "Sguir dheth", "FÀGAIL", "STAD",
			"BRÙTH AIR ESC GUS LEANTAINN AIR ADHART", "Is e an ìre de fhreagairtean ceart agad san obair lèirsinneach",
			"Is e an ìre de fhreagairtean ceart agad san obair sgrùdaidh", " - air ais", "Cruthaich",
			"Deuchainn deuchainn", "SCT_", "AIR AIS", "STAD", // 30
			"Leugh Leabhar-làimhe", "Gu mì-fhortanach cha deach an eadar-theangachadh seo a sgrùdadh fhathast",
			"Gu mì-fhortanach, chan eil ach claisneachd Beurla ri fhaighinn airson a’ chànain seo an-dràsta ",
			"Toradh cuibheasach làitheil", "Coileanadh cuibheasach gach seisean", "Seall a h-uile ruith",
			"PRESS THE SPACE BUTTON TO BEGIN", // 37
			"Tionndadh N-BACK", "Ràinig an ìre as àirde latha", "Ràinig an ìre as àirde seisean",
			"Gluais thairis air gach puing gus an ceann-latha fhaicinn" };

	static String[] sv = { "Skapa användare", "Välj användare", "Börja träna", "Visa resultat", "Store-resultat",
			"Användare:", "Användare", "Användaren finns redan! Skriv över? Alla språkkurser går förlorade!", "Nivå:",
			"Varaktighet:", "Prövningar:", "Visuell", "Auditiv", "Lång tidsträning", "Byt användare utan att spara?",
			"Ja", "Nej", "Skriv över data?", "Spara", "Avbryt", "UTGÅNG", "PAUS", "TRYCK PÅ ESC FÖR ATT FORTSÄTTA",
			"Din frekvens av rätt svar i den visuella uppgiften är", "Din frekvens av rätta svar i hörseluppgiften är",
			" - tillbaka", "Skapa", "Testvolym", "SV_", "TILLBAKA", "STOPP", // 30
			"Läs manual", "Tyvärr har denna översättning ännu inte kontrollerats",
			"Tyvärr är det bara engelskt ljud tillgängligt för det här språket", "Genomsnittlig daglig produktion",
			"Genomsnittlig prestanda per session", "Visa alla körningar", "PRESS RUMMKNAPPEN TILL BEGYNNING", // 37
			"N-BACK version", "Högsta nivå nådde en dag", "Högsta nivå nådde en session",
			"Håll muspekaren över varje punkt för att se datumet" };

	static String[] tm = {

			"பயனரை உருவாக்கு", "பயனரைத் தேர்ந்தெடு", "பயிற்சியைத் தொடங்கு", "முடிவுகளை காட்டு", "ஸ்டோர் முடிவுகள்",
			"பயனர்: ", "பயனர்", "பயனர் ஏற்கனவே இருக்கிறார்! மேலெழுதலாமா? எல்லா மொழி சரங்களும் இழக்கப்படும்!", "நிலை:",
			"காலம்:", "சோதனைகள்:", "விஷுவல்", "ஆடிட்டரி", "நீண்ட கால பயிற்சி", "சேமிக்காமல் பயனரை மாற்றலாமா?", "ஆம்",
			"இல்லை", "தரவை மேலெழுதுமா?", "சேமி", "ரத்துசெய்", "வெளியேறு", "இடைநிறுத்தப்பட்டது", "தொடர ESC தொடரவும்",
			"காட்சி பணியில் உங்கள் சரியான பதில்களின் வீதம்", "செவிவழி பணியில் உங்கள் சரியான பதில்களின் வீதம்",
			" - மீண்டும்", "உருவாக்கு", "சோதனை தொகுதி", "TM_", "மீண்டும்", "நிறுத்து", // 30
			"கையேட்டைப் படியுங்கள்", "துரதிர்ஷ்டவசமாக இந்த மொழிபெயர்ப்பு இன்னும் சரிபார்க்கப்படவில்லை",
			"துரதிர்ஷ்டவசமாக, இந்த மொழிக்கு தற்போது ஆங்கில ஆடியோ மட்டுமே கிடைக்கிறது", "சராசரி தினசரி வெளியீடு",
			"ஒரு அமர்வுக்கு சராசரி செயல்திறன்", "எல்லா ரன்களையும் காட்டு", "தொடங்குவதற்கு ஸ்பேஸ் பட்டனை அழுத்தவும்", // 37
			"என்-பேக் பதிப்பு", "மிக உயர்ந்த நிலை ஒரு நாளை எட்டியது", "மிக உயர்ந்த நிலை ஒரு அமர்வை அடைந்தது",
			"தேதியைக் காண ஒவ்வொரு புள்ளியிலும் வட்டமிடுங்கள்" };

	static String[] tr = { "Kullanıcı oluştur", "Kullanıcı seç", "Antrenmana başla", "Sonuçları göster",
			"Mağaza Sonuçları", "Kullanıcı:", "Kullanıcı",
			"Kullanıcı zaten var! Üzerine yaz? Tüm languageStrings kaybedilecek!", "Seviye:", "Süre:", "Denemeler:",
			"Görsel", "İşitsel", "Uzun Süreli Eğitim", "Kullanıcı kaydetmeden değiştirilsin mi?", "Evet", "Hayır",
			"Verilerin Üzerine Yazılsın mı?", "Kayıt etmek", "İptal etmek", "ÇIKIŞ", "DURAKLATILDI",
			"DEVAM ETMEK IÇIN ESC TUŞUNA BASIN", "Görsel görevdeki doğru cevap oranınız",
			"İşitsel görevdeki doğru cevap oranınız", " - geri", "Oluşturmak", "Test Hacmi", "TR_", "GERİ", "DURDUR", // 30
			"Kılavuzu Oku", "Maalesef bu çeviri henüz kontrol edilmedi",
			"Maalesef şu anda bu dil için yalnızca İngilizce ses var", "Ortalama günlük çıktı",
			"Oturum başına ortalama performans", "Tüm koşuları göster", "BAŞLANGIÇ İÇİN ALAN DÜĞMESİNE BASIN", // 37
			"N-BACK sürümü", "Bir güne en yüksek seviyeye ulaştı", "En üst düzey bir oturuma ulaştı",
			"Tarihi görmek için fareyle her noktanın üzerine gelin" };

	static String[] ukr = { "Створити користувача", "Вибрати користувача", "Почніть тренування", "Показати результати",
			"Зберігати результати", "Користувач:", "Користувач",
			"Користувач вже існує! Перепишіть? Усі мовиСтринги будуть втрачені!", "Рівень:", "Тривалість:",
			"Випробування:", "Візуальний", "Слуховий", "Тривалий час навчання", "Змінити користувача без збереження?",
			"Так", "Ні", "Перезаписати дані?", "Зберегти", "Скасувати", "ВИХІД", "ЗАПАСНЕНО",
			"ДИСКУВАННЯ ESC ПРОДОВЖИТИ", "Ваша швидкість правильних відповідей у візуальному завданні",
			"Ваша швидкість правильних відповідей у слуховому завданні", " - назад", "Створити", "Тестовий об'єм",
			"UKR_", "НАЗАД", "СТОП", // 30
			"Прочитати посібник", "На жаль, цей переклад ще не перевірено",
			"На жаль, для цієї мови наразі доступне лише англійське аудіо", "Середньодобовий вихід",
			"Середня ефективність за сеанс", "Показати всі прогони", "НАДАЙТЕ ПРОСТІ КНОПКУ НА ПОЧАТКУ", // 37
			"Версія N-BACK", "Найвищий рівень досягнуто за день", "Найвищий рівень досяг сесії",
			"Наведіть курсор миші на кожну точку, щоб побачити дату" };

	public static String word(int language, int word) {
		lngLst.add(firstStart);
		lngLst.add(ak);
		lngLst.add(cz);
		lngLst.add(dn);
		lngLst.add(en);
		lngLst.add(es);
		lngLst.add(est);
		lngLst.add(fl);
		lngLst.add(fr);
		lngLst.add(fsk);
		lngLst.add(ge);

		lngLst.add(gr);
		lngLst.add(hd);
		lngLst.add(it);
		lngLst.add(jp);
		lngLst.add(kr);
		lngLst.add(nl);
		lngLst.add(nw);
		lngLst.add(pl);
		lngLst.add(pt);
		lngLst.add(ru);

		lngLst.add(sc);
		lngLst.add(sv);
		lngLst.add(sct);
		lngLst.add(tm);
		lngLst.add(tr);
		lngLst.add(ukr);
		lngLst.add(is);
		lngLst.add(ir);
		lngLst.add(pj);
		lngLst.add(geo);

		String[] lang = lngLst.get(language);
		return lang[word];
	}

}
