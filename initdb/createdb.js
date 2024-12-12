// Initialize the database with test data
db = db.getSiblingDB("project-pfe");

// Create collections
db.createCollection("forms");
db.createCollection("companies");
db.createCollection("users");
db.createCollection("answers");
db.createCollection("questions");

// Insert data into the forms collection
db.forms.insert({
  _id: ObjectId("67570c91b867304c50e60b0d"),
  formId: 1,
  companyId: 6,
  total: 81,
  completed: 7,
  createdAt: new Date("2024-12-09T15:28:17.138Z"),
  questionList: [
    {
      _id: ObjectId("6756f91015c861ffb5570b37"),
      questionId: 6,
      question:
        "Avez-vous mis en place des initiatives pour sensibiliser vos travailleur·euses sur les bonnes pratiques en matière de consommation d'énergie ?",
      choice: [
        { choice: "Oui", poids: 1 },
        { choice: "Non", poids: 0 },
        { choice: "N/A - Nous n'avons pas de travailleur·euses.", poids: 0 },
      ],
      enjeux: "GESTION DE L'ENERGIE",
      category: "ENERGIE & CARBONE",
      pilier: "\u0000",
      template: "WORKERS",
      type: "radio",
    },
    {
      _id: ObjectId("6756f91015c861ffb5570b3e"),
      questionId: 13,
      question:
        "Proposez-vous des ateliers de sensibilisation et des formations sur les émissions de gaz à effet de serre à destination de vos travailleur·euses ? (par exemple, la fresque du climat)",
      choice: [
        { choice: "Oui", poids: 0 },
        { choice: "Non", poids: 0 },
        { choice: "N/A", poids: 0 },
      ],
      enjeux: "EMPREINTE CARBONE",
      category: "ENERGIE & CARBONE",
      pilier: "\u0000",
      template: "WORKERS",
      type: "radio",
    },
    {
      _id: ObjectId("6756f91015c861ffb5570b46"),
      questionId: 21,
      question:
        "Avez-vous mis en place des initiatives pour sensibiliser vos travailleur·euses à la gestion de l'eau et à sa consommation ?",
      choice: [
        { choice: "Oui", poids: 0 },
        { choice: "Non", poids: 0 },
        { choice: "N/A - Nous n'avons pas de travailleur·euses.", poids: 0 },
      ],
      enjeux: "EAU",
      category: "EAU, MATIERES PREMIERES ET FOURNITURES",
      pilier: "\u0000",
      template: "WORKERS",
      type: "radio",
    },
    {
      _id: ObjectId("6756f91015c861ffb5570b49"),
      questionId: 24,
      question:
        "Avez-vous mis en place des initiatives pour sensibiliser vos travailleur·euses à l'utilisation des ressources ?",
      choice: [
        { choice: "Oui", poids: 2 },
        { choice: "Non", poids: 0 },
        { choice: "N/A", poids: 0 },
      ],
      enjeux: "MATIERES PREMIERES ET FOURNITURES",
      category: "EAU, MATIERES PREMIERES ET FOURNITURES",
      pilier: "\u0000",
      template: "WORKERS",
      type: "radio",
    },
    {
      _id: ObjectId("6756f91015c861ffb5570b4f"),
      questionId: 30,
      question:
        "Avez-vous mis en place des initiatives pour sensibiliser vos travailleur·euses à la réduction, la gestion et le recyclage des déchets ?",
      choice: [
        { choice: "Oui", poids: 0 },
        { choice: "Non", poids: 0 },
        { choice: "N/A - Nous n'avons pas de travailleur·euses.", poids: 0 },
      ],
      enjeux: "DÉCHETS",
      category: "DÉCHETS",
      pilier: "\u0000",
      template: "WORKERS",
      type: "radio",
    },
    {
      _id: ObjectId("6756f91015c861ffb5570b52"),
      questionId: 33,
      question:
        "Proposez-vous des ateliers de sensibilisation et des formations sur biodiversité à destination de vos travailleur·euses ?",
      choice: [
        { choice: "Oui", poids: 1 },
        { choice: "Non", poids: 0 },
        { choice: "N/A - Nous n'avons pas de travailleur·euses.", poids: 0 },
      ],
      enjeux: "ÉCOSYSTÈMES ET BIODIVERSITÉ",
      category: "ÉCOSYSTÈMES ET BIODIVERSITÉ",
      pilier: "\u0000",
      template: "WORKERS",
      type: "radio",
    },
    {
      _id: ObjectId("6756f91015c861ffb5570b53"),
      questionId: 34,
      question:
        "Comment XXX crée-t-elle un processus de recrutement et d'embauche inclusif ?",
      choice: [
        {
          choice:
            "Nous incluons dans toutes nos offres d'emploi une déclaration d'engagement en faveur de la diversité, de l'équité et de l'inclusion.",
          poids: 0,
        },
        {
          choice:
            "Nous analysons la formulation et les exigences de nos descriptions de poste pour nous assurer qu'elles sont inclusives et équitables.",
          poids: 0,
        },
        {
          choice:
            "Nous recrutons activement par l'intermédiaire d'organisations ou de services qui s'adressent à des personnes souvent exclues du marché du travail (ex : personnes en situation de handicap, personnes qui ont été incarcérées, demandeurs d'emploi jeunes ou de longue durée, etc.)",
          poids: 0,
        },
        {
          choice:
            'Nous procédons à des examens anonymes ou "à l\'aveugle" des candidatures ou des CV sans y associer de noms ou de caractéristiques identifiables.',
          poids: 0,
        },
        {
          choice: "Autre : veuillez expliquer dans les commentaires.",
          poids: 0,
        },
        { choice: "Rien de tout cela.", poids: 0 },
        { choice: "N/A - Nous n'avons jamais recruté.", poids: 0 },
      ],
      enjeux: "INCLUSION ET EQUITE",
      category: "DIVERSITE, INCLUSION ET EQUITE",
      pilier: "\u0000",
      template: "WORKERS",
      type: "checkbox",
    },
  ],
});

// Insert data into the companies collection
db.companies.insertMany([
  {
    _id: ObjectId("6751913d15c861ffb5570796"),
    companyId: 1,
    name: "Tech Inovators Inc.",
    companyNumber: "1234560989878",
    legalForm: "SARL",
    address: "L'adresse de la company",
    naceCode: "1234",
    registrationDate: new Date("2024-12-06T16:11:53.674Z"),
    templates: ["FACILITY", "ALL"],
    login: "tec234",
    password: "$2a$10$2185EUnR3DS7dUKseirbFuOi6v.S6MtkUAf.5ncZYjJ.IXF5tEgFO",
    isValidated: true,
    contactEmail: "ouarrad.dania@gmail.com",
    numberOfWorkers: 0,
    sellsProduct: false,
    isOwner: false,
    _class: "be.vinci.ipl.pfe.group05.shiftingpact.models.Company",
  },
  {
    _id: ObjectId("6751d38615c861ffb55707c0"),
    companyId: 2,
    name: "Future Vision Ltd.",
    companyNumber: "9876543211234",
    legalForm: "SA",
    address: "123 Innovation Street",
    naceCode: "5678",
    registrationDate: new Date("2024-12-05T13:30:00Z"),
    templates: ["FACILITY", "ALL"],
    login: "fut123",
    password: "$2a$10$G1QweDf78sUOeiJ56opK3.GtF1zBoiuDX4yq2JRUV1mTvcFS1/PaQ",
    isValidated: true,
    contactEmail: "contact@futurevision.com",
    numberOfWorkers: 0,
    sellsProduct: false,
    isOwner: false,
    _class: "be.vinci.ipl.pfe.group05.shiftingpact.models.Company",
  },
  {
    _id: ObjectId("6752ea6115c861ffb557081d"),
    companyId: 4,
    name: "Test Didia",
    companyNumber: "BE9876543210",
    legalForm: "Société à Responsabilité Limitée",
    address: "45 Innovation Drive, Antwerp, Belgium",
    naceCode: "1234",
    registrationDate: new Date("2024-12-06T12:13:38.708Z"),
    templates: ["FACILITY", "ALL"],
    login: "tes234",
    password: "$2a$10$glsiS6NHrazrRtJH7uAIB.vWyTIlqu7RMV.rHlSeQtXxeHTcYIimy",
    isValidated: true,
    contactEmail: "fechadodidia@hotmail.com",
    numberOfWorkers: 0,
    sellsProduct: false,
    isOwner: false,
    _class: "be.vinci.ipl.pfe.group05.shiftingpact.models.Company",
  },
]);

// Insert data into the users collection
db.users.insert({
  _id: ObjectId("6751c57f15c861ffb55707b6"),
  userId: 1,
  email: "admin@example.com",
  last_name: "Doe",
  first_name: "John",
  password: "$2a$12$tphULG4hd5dRfw3oTaJpqOwqt2vT3anE/6tEQaVRV6up6uxmnQuv6",
});

// Insert data into the answers collection
db.answers.insert({
  _id: ObjectId("675374f1a93d164abed7cb39"),
  questionId: 1,
  response: "Non",
  comments: "Additional comments",
  _class: "be.vinci.ipl.pfe.group05.shiftingpact.models.Answer",
});

// Insert data into the questions collection
db.questions.insertMany([
  {
    _id: ObjectId("6756f91015c861ffb5570b32"),
    questionId: 1,
    question: "Suivez-vous la consommation d'énergie de XXX ?",
    category: "ENERGIE & CARBONE",
    type: "radio",
    choice: [
      { choice: "Oui", poids: 2 },
      { choice: "Non", poids: 0 },
    ],
    template: "ALL",
    enjeux: "GESTION DE L'ENERGIE",
    pillar: "E",
  },
  {
    _id: ObjectId("6756f91015c861ffb5570b33"),
    questionId: 2,
    question:
      "Si vous la suivez, veuillez indiquer votre consommation d'énergie totale des 12 derniers mois (en kWh)",
    category: "ENERGIE & CARBONE",
    type: "champ libre",
    choice: [],
    template: "ALL",
    enjeux: "GESTION DE L'ENERGIE",
    pillar: "E",
  },
  {
    _id: ObjectId("6756f91015c861ffb5570b34"),
    questionId: 3,
    question: "Avez-vous un contrat vert avec votre fournisseur d'énergie ?",
    category: "ENERGIE & CARBONE",
    type: "radio",
    choice: [
      { choice: "Oui", poids: 1 },
      { choice: "Non", poids: 0 },
      { choice: "Je ne sais pas", poids: 0 },
      { choice: "N/A", poids: 0 },
    ],
    template: "OWNED FACILITY",
    enjeux: "GESTION DE L'ENERGIE",
    pillar: "E",
  },
  {
    _id: ObjectId("6756f91015c861ffb5570b35"),
    questionId: 4,
    question:
      "Produisez-vous de l'électricité sur site (par exemple, avec des panneaux solaires) ?",
    category: "ENERGIE & CARBONE",
    type: "radio",
    choice: [
      { choice: "Oui", poids: 1 },
      { choice: "Non", poids: 0 },
      { choice: "Je ne sais pas", poids: 0 },
      { choice: "N/A", poids: 0 },
    ],
    template: "OWNED FACILITY",
    enjeux: "GESTION DE L'ENERGIE",
    pillar: "E",
  },
  {
    _id: ObjectId("6756f91015c861ffb5570b36"),
    questionId: 5,
    question:
      "Qu'avez-vous mis en place pour limiter votre consommation d'énergie ? Veuillez sélectionner toutes les réponses applicables.",
    category: "ENERGIE & CARBONE",
    type: "checkbox",
    choice: [
      {
        choice:
          "Nous avons investi dans des équipements de bureau économes en énergie, notamment des ordinateurs, des imprimantes, etc.",
        poids: 1,
      },
      {
        choice:
          "Nous avons remplacé les ampoules traditionnelles par des ampoules LED à haut rendement énergétique afin de réduire la consommation d'électricité.",
        poids: 1,
      },
      {
        choice:
          "Nous avons installé des thermostats intelligents pour optimiser les systèmes de chauffage, de ventilation et de climatisation (CVC) en vue d'une meilleure efficacité énergétique.",
        poids: 1,
      },
      {
        choice:
          "Nous réalisons régulièrement des audits énergétiques afin d'identifier les points à améliorer et d'optimiser notre consommation d'énergie.",
        poids: 1,
      },
      {
        choice:
          "Nous avons installé des détecteurs de présence pour l'éclairage et les systèmes CVC afin d'ajuster automatiquement la consommation en fonction des niveaux d'occupation.",
        poids: 1,
      },
      { choice: "Autre : veuillez expliquer dans les commentaires", poids: 0 },
      { choice: "Aucune de ces réponses.", poids: 0 },
      { choice: "N/A", poids: 0 },
    ],
    template: "OWNED FACILITY",
    enjeux: "GESTION DE L'ENERGIE",
    pillar: "E",
  },
  {
    _id: ObjectId("6756f91015c861ffb5570b37"),
    questionId: 6,
    question:
      "Avez-vous mis en place des initiatives pour sensibiliser vos travailleur·euses sur les bonnes pratiques en matière de consommation d'énergie ?",
    category: "ENERGIE & CARBONE",
    type: "radio",
    choice: [
      { choice: "Oui", poids: 1 },
      { choice: "Non", poids: 0 },
      { choice: "N/A - Nous n'avons pas de travailleur·euses.", poids: 0 },
    ],
    template: "WORKERS",
    enjeux: "GESTION DE L'ENERGIE",
    pillar: "E",
  },
  {
    _id: ObjectId("6756f91015c861ffb5570b38"),
    questionId: 7,
    question: "Avez-vous calculé l'empreinte carbone des activités de XXX ?",
    category: "ENERGIE & CARBONE",
    type: "radio",
    choice: [
      {
        choice:
          "Oui, pour les Scopes 1 & 2 (les émissions de gaz à effet de serre directes et indirectes liées à notre consommation d'énergie).",
        poids: 0.5,
      },
      {
        choice:
          "Oui, pour le Scope 3 (toutes les émissions de GES indirectes liées à nos activités).",
        poids: 1.5,
      },
      { choice: "Non", poids: 0 },
    ],
    template: "ALL",
    enjeux: "EMPREINTE CARBONE",
    pillar: "E",
  },
  {
    _id: ObjectId("6756f91015c861ffb5570b39"),
    questionId: 8,
    question:
      "Si vous la calculez, veuillez indiquer votre empreinte carbone de Scope 1 (en tonnes de CO2 équivalent) :",
    category: "ENERGIE & CARBONE",
    type: "champ libre",
    choice: [],
    template: "ALL",
    enjeux: "EMPREINTE CARBONE",
    pillar: "E",
  },
  {
    _id: ObjectId("6756f91015c861ffb5570b3a"),
    questionId: 9,
    question:
      "Si vous la calculez, veuillez indiquer votre empreinte carbone de Scope 2 (en tonnes de CO2 équivalent) :",
    category: "ENERGIE & CARBONE",
    type: "champ libre",
    choice: [],
    template: "ALL",
    enjeux: "EMPREINTE CARBONE",
    pillar: "E",
  },
  {
    _id: ObjectId("6756f91015c861ffb5570b3b"),
    questionId: 10,
    question:
      "Si vous la calculez, veuillez indiquer votre empreinte carbone de Scope 3 (en tonnes de CO2 équivalent) :",
    category: "ENERGIE & CARBONE",
    type: "champ libre",
    choice: [],
    template: "ALL",
    enjeux: "EMPREINTE CARBONE",
    pillar: "E",
  },
  {
    _id: ObjectId("6756f91015c861ffb5570b3c"),
    questionId: 11,
    question:
      "Si vous avez réalisé un rapport d'empreinte carbone pour XXX, que comprend-il ? Veuillez sélectionner toutes les réponses applicables.",
    category: "ENERGIE & CARBONE",
    type: "checkbox",
    choice: [
      { choice: "Nous n'avons pas réalisé de rapport d'empreinte carbone.", poids: 0 },
      {
        choice:
          "Notre rapport d'empreinte carbone inclut une déclaration claire de notre engagement en faveur de la réduction de notre empreinte carbone.",
        poids: 0.42,
      },
      {
        choice:
          "Notre rapport d'empreinte carbone présente une ventilation des émissions de carbone par source ou catégorie.",
        poids: 0.42,
      },
      {
        choice:
          "Notre rapport d'empreinte carbone décrit de manière transparente la méthodologie utilisée, le champ d'application, le processus de collecte de données, les hypothèses, les incertitudes et les limites du calcul.",
        poids: 0.42,
      },
      {
        choice:
          "Notre rapport d'empreinte carbone présente nos objectifs de réduction et décrit les mesures prises pour les atteindre.",
        poids: 0.42,
      },
      {
        choice:
          "Notre rapport d'empreinte carbone est conforme à la méthodologie Bilan Carbone® et/ou au GHG Protocol (il a été réalisé via un expert et/ou une plateforme agréée).",
        poids: 0.42,
      },
      {
        choice:
          "Nous partageons publiquement notre rapport d'empreinte carbone complet (en ce compris tous les points cochés ci-dessus).",
        poids: 0.42,
      },
      { choice: "N/A", poids: 0 },
    ],
    template: "ALL",
    enjeux: "EMPREINTE CARBONE",
    pillar: "E",
  },
]);

