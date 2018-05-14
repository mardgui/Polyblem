CREATE TABLE issue(

	idIssue INTEGER AUTO_INCREMENT PRIMARY KEY,
	titleIssue TEXT NOT NULL,
	category TEXT CHECK (category IN ('Manque','Casse','Dysfonctionnement', 'Propreté', 'Autre')),
	description TEXT,
	pictureURL TEXT,
	location TEXT,
	locationDetails TEXT,
	urgency TEXT CHECK (category IN ('Faible','Moyen','Forte')),
	email TEXT, 
	dateIssue TEXT
)


DROP TABLE IF EXISTS issue;