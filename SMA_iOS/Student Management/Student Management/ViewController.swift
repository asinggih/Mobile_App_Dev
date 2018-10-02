//
//  ViewController.swift
//  Student Management
//
//  Created by Aditya Singgih on 30/9/18.
//  Copyright © 2018 Aditya Singgih. All rights reserved.
//

import UIKit


public class StudentCell: UITableViewCell {
    
    @IBOutlet weak var sPic: UIImageView!
    @IBOutlet weak var sName: UILabel!
    @IBOutlet weak var sID: UILabel!
    @IBOutlet weak var sProgram: UILabel!
    
}


class ViewController: UIViewController {
    
    var programs:[Program] = []
    var studentList:[Student] = []
    
    @IBOutlet weak var studentsTableView: UITableView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        let context = appDelegate.persistentContainer.viewContext

        /* -------------------------------------------------
                        Preload Study Programs
        -------------------------------------------------*/
        
//        let programList = ["Master of ICT",
//                       "Master of Data Science",
//                       "Bachelor of Arts",
//                       "Bachelor of Engineering",
//                       "Bachelor of Science"]
//
//        for item in programList {
//            let program = Program(context: context)
//            program.name = item
//        }
//
//        appDelegate.saveContext()
//        
        do{
            studentList = try context.fetch(Student.fetchRequest())
        }
        catch{
            studentList = []
        }
        
        studentsTableView.delegate = self
        studentsTableView.dataSource = self
        
        
        print(programs)
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}

extension ViewController: UITableViewDelegate{
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    

}

extension ViewController: UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return studentList.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        // init the cell
        let studentCell = tableView.dequeueReusableCell(withIdentifier: "studentCell", for: indexPath) as! StudentCell
        
        let student = studentList[indexPath.row]
        
        studentCell.sName.text = "\(student.firstName!) \(student.lastName!)"
        studentCell.sID.text = "1234"
        studentCell.sProgram.text = student.programs?.name
        return studentCell
    }


}








