//
//  examListVC.swift
//  Student Management
//
//  Created by Aditya Singgih on 4/10/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import UIKit

public class ExamCell: UITableViewCell{
    
    @IBOutlet weak var examTitle: UILabel!
    @IBOutlet weak var examDate: UILabel!
    @IBOutlet weak var examTime: UILabel!
    @IBOutlet weak var examLocation: UILabel!
    
    
}

class examListVC: UIViewController {

    @IBOutlet weak var examTableView: UITableView!
    
    var examList:[Exam] = []
    
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    override func viewDidLoad() {
        super.viewDidLoad()

        getExams()
        NotificationCenter.default.addObserver(self, selector: #selector(refreshTable), name: NSNotification.Name(rawValue: "reloadExam"), object: nil)
        
        examTableView.delegate = self
        examTableView.dataSource = self
        
        
    }
    
    @objc func refreshTable(notification: NSNotification){
        getExams()
        self.examTableView.reloadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    private func getExams() {
        do{
            examList = try context.fetch(Exam.fetchRequest())
        }
        catch{
            examList = []
        }
    }


}


extension examListVC: UITableViewDelegate{
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
}

extension examListVC: UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return examList.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let examCell = tableView.dequeueReusableCell(withIdentifier: "examCell", for: indexPath) as! ExamCell
        
        let exam = examList[indexPath.row]
        
        examCell.examTitle.text = exam.name
        examCell.examTime.text = exam.time
        examCell.examLocation.text = exam.location
        
        return examCell
    }


}



extension UIView {
    func fadeTransition(_ duration:CFTimeInterval) {
        let animation = CATransition()
        animation.timingFunction = CAMediaTimingFunction(name:
            kCAMediaTimingFunctionEaseInEaseOut)
        animation.type = kCATransitionFade
        animation.duration = duration
        layer.add(animation, forKey: kCATransitionFade)
    }
}








