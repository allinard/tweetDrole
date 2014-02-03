# Résultats Solution 1 de la semaine 4
---------

### En quelques mots : 
On a enlevé l'attribut "categorie" représentant les sous catégories (PasDrole, Autodérision, Contrepetrie, ...)
L'attribut "word" est : "si un tweet contient un mot drole, alors true sinon false"


## Naive Bayes

Naive Bayes Classifier

                  Class
Attribute          true    false
                 (0.48)   (0.52)
=================================
user
  true            1916.0      1.0
  false           2903.0   5283.0
  [total]         4819.0   5284.0

word
  true             335.0     15.0
  false           4484.0   5269.0
  [total]         4819.0   5284.0

hashtag
  true             101.0      1.0
  false           4718.0   5283.0
  [total]         4819.0   5284.0

smiley
  true             100.0    161.0
  false           4719.0   5123.0
  [total]         4819.0   5284.0

argoInternet
  true             335.0     15.0
  false           4484.0   5269.0
  [total]         4819.0   5284.0

ponctuation
  absent          3965.0   4384.0
  regulier         306.0    313.0
  surnombre        549.0    588.0
  [total]         4820.0   5285.0

retweet
  true             167.0   1020.0
  false           4652.0   4264.0
  [total]         4819.0   5284.0

nbRetweet
  mean          207.5426  23.9067
  std. dev.     780.1427 147.9292
  weight sum        4817     5282
  precision      29.0755  29.0755

longeur
  mean          101.0893 103.6237
  std. dev.       34.537  31.7723
  weight sum        4817     5282
  precision       1.2667   1.2667



Time taken to build model: 0.08 seconds

=== Evaluation on training set ===
=== Summary ===

Correctly Classified Instances        7482               74.0865 %
Incorrectly Classified Instances      2617               25.9135 %
Kappa statistic                          0.4686
Mean absolute error                      0.2772
Root mean squared error                  0.4647
Relative absolute error                 55.5622 %
Root relative squared error             93.035  %
Total Number of Instances            10099     

=== Detailed Accuracy By Class ===

               TP Rate   FP Rate   Precision   Recall  F-Measure   ROC Area  Class
                 0.471     0.013      0.971     0.471     0.634      0.77     true
                 0.987     0.529      0.672     0.987     0.799      0.77     false
Weighted Avg.    0.741     0.283      0.814     0.741     0.721      0.77 

=== Confusion Matrix ===

    a    b   <-- classified as
 2268 2549 |    a = true
   68 5214 |    b = false



## J48
J48 pruned tree
------------------

user = true: true (1915.0)
user = false
|   word = true
|   |   retweet = true: false (5.0/1.0)
|   |   retweet = false: true (237.0/10.0)
|   word = false
|   |   retweet = true: false (1042.0/27.0)
|   |   retweet = false
|   |   |   longeur <= 135
|   |   |   |   nbRetweet <= 993
|   |   |   |   |   nbRetweet <= 1
|   |   |   |   |   |   ponctuation = absent
|   |   |   |   |   |   |   longeur <= 131: false (1696.0/528.0)
|   |   |   |   |   |   |   longeur > 131
|   |   |   |   |   |   |   |   smiley = true: false (3.0)
|   |   |   |   |   |   |   |   smiley = false
|   |   |   |   |   |   |   |   |   nbRetweet <= 0: true (125.0/48.0)
|   |   |   |   |   |   |   |   |   nbRetweet > 0: false (64.0/29.0)
|   |   |   |   |   |   ponctuation = regulier
|   |   |   |   |   |   |   smiley = true: false (11.0/1.0)
|   |   |   |   |   |   |   smiley = false
|   |   |   |   |   |   |   |   nbRetweet <= 0: true (136.0/60.0)
|   |   |   |   |   |   |   |   nbRetweet > 0
|   |   |   |   |   |   |   |   |   longeur <= 126: true (26.0/10.0)
|   |   |   |   |   |   |   |   |   longeur > 126: false (8.0)
|   |   |   |   |   |   ponctuation = surnombre
|   |   |   |   |   |   |   smiley = true
|   |   |   |   |   |   |   |   longeur <= 107: false (8.0)
|   |   |   |   |   |   |   |   longeur > 107: true (2.0)
|   |   |   |   |   |   |   smiley = false
|   |   |   |   |   |   |   |   nbRetweet <= 0: true (256.0/92.0)
|   |   |   |   |   |   |   |   nbRetweet > 0
|   |   |   |   |   |   |   |   |   longeur <= 109: true (59.0/22.0)
|   |   |   |   |   |   |   |   |   longeur > 109: false (34.0/11.0)
|   |   |   |   |   nbRetweet > 1
|   |   |   |   |   |   nbRetweet <= 76
|   |   |   |   |   |   |   longeur <= 121
|   |   |   |   |   |   |   |   longeur <= 47
|   |   |   |   |   |   |   |   |   nbRetweet <= 10
|   |   |   |   |   |   |   |   |   |   longeur <= 41: true (16.0/6.0)
|   |   |   |   |   |   |   |   |   |   longeur > 41
|   |   |   |   |   |   |   |   |   |   |   nbRetweet <= 4: false (7.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   nbRetweet > 4: true (4.0/1.0)
|   |   |   |   |   |   |   |   |   nbRetweet > 10: false (17.0/3.0)
|   |   |   |   |   |   |   |   longeur > 47: false (1698.0/229.0)
|   |   |   |   |   |   |   longeur > 121
|   |   |   |   |   |   |   |   nbRetweet <= 13: false (358.0/53.0)
|   |   |   |   |   |   |   |   nbRetweet > 13
|   |   |   |   |   |   |   |   |   longeur <= 130
|   |   |   |   |   |   |   |   |   |   nbRetweet <= 33: true (92.0/29.0)
|   |   |   |   |   |   |   |   |   |   nbRetweet > 33: false (24.0/9.0)
|   |   |   |   |   |   |   |   |   longeur > 130: false (55.0/8.0)
|   |   |   |   |   |   nbRetweet > 76
|   |   |   |   |   |   |   ponctuation = absent
|   |   |   |   |   |   |   |   nbRetweet <= 181: true (228.0/102.0)
|   |   |   |   |   |   |   |   nbRetweet > 181: false (110.0/37.0)
|   |   |   |   |   |   |   ponctuation = regulier
|   |   |   |   |   |   |   |   nbRetweet <= 883: false (25.0/1.0)
|   |   |   |   |   |   |   |   nbRetweet > 883: true (3.0/1.0)
|   |   |   |   |   |   |   ponctuation = surnombre: false (31.0/7.0)
|   |   |   |   nbRetweet > 993: true (85.0/4.0)
|   |   |   longeur > 135
|   |   |   |   nbRetweet <= 1: true (1142.0/233.0)
|   |   |   |   nbRetweet > 1
|   |   |   |   |   nbRetweet <= 291
|   |   |   |   |   |   nbRetweet <= 2
|   |   |   |   |   |   |   longeur <= 136: true (55.0/15.0)
|   |   |   |   |   |   |   longeur > 136: false (71.0/21.0)
|   |   |   |   |   |   nbRetweet > 2
|   |   |   |   |   |   |   nbRetweet <= 91: false (374.0/56.0)
|   |   |   |   |   |   |   nbRetweet > 91
|   |   |   |   |   |   |   |   ponctuation = absent
|   |   |   |   |   |   |   |   |   longeur <= 138
|   |   |   |   |   |   |   |   |   |   nbRetweet <= 134: true (5.0/1.0)
|   |   |   |   |   |   |   |   |   |   nbRetweet > 134
|   |   |   |   |   |   |   |   |   |   |   longeur <= 136
|   |   |   |   |   |   |   |   |   |   |   |   nbRetweet <= 197: false (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   nbRetweet > 197: true (2.0)
|   |   |   |   |   |   |   |   |   |   |   longeur > 136: false (9.0)
|   |   |   |   |   |   |   |   |   longeur > 138
|   |   |   |   |   |   |   |   |   |   nbRetweet <= 154
|   |   |   |   |   |   |   |   |   |   |   longeur <= 139
|   |   |   |   |   |   |   |   |   |   |   |   nbRetweet <= 105: false (3.0)
|   |   |   |   |   |   |   |   |   |   |   |   nbRetweet > 105: true (5.0/2.0)
|   |   |   |   |   |   |   |   |   |   |   longeur > 139
|   |   |   |   |   |   |   |   |   |   |   |   nbRetweet <= 127: true (4.0)
|   |   |   |   |   |   |   |   |   |   |   |   nbRetweet > 127: false (5.0/1.0)
|   |   |   |   |   |   |   |   |   |   nbRetweet > 154: true (8.0)
|   |   |   |   |   |   |   |   ponctuation = regulier: false (3.0/1.0)
|   |   |   |   |   |   |   |   ponctuation = surnombre: false (5.0)
|   |   |   |   |   nbRetweet > 291: true (26.0/2.0)

Number of Leaves  : 	48

Size of the tree : 	92


Time taken to build model: 0.59 seconds

=== Evaluation on training set ===
=== Summary ===

Correctly Classified Instances        8437               83.5429 %
Incorrectly Classified Instances      1662               16.4571 %
Kappa statistic                          0.669 
Mean absolute error                      0.2388
Root mean squared error                  0.3455
Relative absolute error                 47.8599 %
Root relative squared error             69.1809 %
Total Number of Instances            10099     

=== Detailed Accuracy By Class ===

               TP Rate   FP Rate   Precision   Recall  F-Measure   ROC Area  Class
                 0.787     0.121      0.856     0.787     0.82       0.904    true
                 0.879     0.213      0.819     0.879     0.848      0.904    false
Weighted Avg.    0.835     0.169      0.837     0.835     0.835      0.904

=== Confusion Matrix ===

    a    b   <-- classified as
 3793 1024 |    a = true
  638 4644 |    b = false


